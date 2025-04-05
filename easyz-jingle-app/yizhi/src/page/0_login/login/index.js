import React, {Component} from 'react';
import {
  StatusBar,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from 'react-native';
import {CodeField, Cursor} from 'react-native-confirmation-code-field';
import {Input} from 'react-native-elements';
import {inject, observer} from 'mobx-react';
import AsyncStorage from '@react-native-community/async-storage';
import IconFont from '@src/component/IconFont';
import {pxToDp} from '@src/util/pxToDp';
import Validator from '@src/util/Validator';
import Request from '@src/util/Request';
import {LOGIN_LOGIN, LOGIN_SEND} from '@src/util/Api';
import GradientButton from '@src/component/GradientButton';
import Toast from '@src/util/Toast';
// 获取数据和监控数据改变
@inject('RootStore')
@observer
class Index extends Component {
  /**
   * 初始化
   */
  state = {
    mobile: '18018594399',
    phoneValid: true,
    // 是否显示登录页面
    showLogin: true,
    // 验证码输入框的值
    verificationCode: '',
    // 倒计时按钮的文本
    btnText: '重新获取',
    // 是否在倒计时中
    isCountDowning: false,
  };
  /**
   * 手机号文本改变
   * @param {string} mobile
   */
  mobileChangeText = mobile => {
    this.setState({mobile});
    console.log(mobile);
  };
  /**
   * 手机号结束编辑
   * @returns
   */
  mobileSubmitEditing = async () => {
    /*
    1 手机号合法校验-正则
      1 不通过 提示
    2 将手机号发送到后台对应接口->获取验证码
      1 发送异步请求的时候 自动的显示等待框
      2 请求回来  等待框  自动隐藏
      3 关键
        1 等待框 ?
        2 自动 ? -> axios的 拦截器
    3 切换到验证码界面
    */
    const {mobile} = this.state;
    const phoneValid = Validator.validatePhone(mobile);
    if (!phoneValid) {
      // phoneValid: false
      this.setState({phoneValid});
      return;
    }
    // phoneValid: true
    const res = await Request.post(LOGIN_SEND, {mobile: mobile});
    console.log(res);
    if (res.ok) {
      // 请求结束
      this.setState({showLogin: false});
      // 开启定时器
      this.countDown();
    } else if (res.message != null) {
      // 请求结束
      Toast.message(res.message, 2000, 'center');
      return;
    } else {
      Toast.sad('请求出错', 2400, 'center');
      return;
    }
  };
  /**
   * 验证码文本改变
   * @param {string} verificationCode
   */
  onVerificationCodeChangeText = verificationCode => {
    this.setState({verificationCode});
  };
  /**
   * 验证码结束编辑
   * @returns
   */
  onVerificationCodeSubmitEditing = async () => {
    /*
    1 对验证码做校验  长度
    2 将手机号码和验证码 一起发送到后台
    2.5
      将用户数据存放到 mobx中
    3 返回值 有 isNew
    4 新用户 -> 完善个人信息的页面
    5 老用户 -> 交友 - 首页
     */
    const {verificationCode, mobile} = this.state;
    if (verificationCode.length !== 6) {
      Toast.message('请输入6位验证码', 2000, 'center');
      return;
    }
    const res = await Request.post(LOGIN_LOGIN, {
      mobile: mobile,
      verificationCode: verificationCode,
    });
    if (!res.ok) {
      console.log(res);
      Toast.sad(res.message, 2000, 'center');
      return;
    }
    //  存储用户数据到 mobx中
    this.props.RootStore.setUserInfo(mobile, res.data.token, mobile);
    //  存储用户数据到 本地缓存中  永久
    AsyncStorage.setItem(
      'userinfo',
      JSON.stringify({
        mobile: mobile,
        token: res.data.token,
        userId: mobile,
      }),
    );
    if (res.data.isNew == null) {
      Toast.sad('请求出错', 2400, 'center');
      return;
    }
    if (res.data.isNew) {
      //  新用户 UserInfo
      this.props.navigation.navigate('UserInfo');
    } else {
      //  老用户
      alert('已登录');
      this.props.navigation.navigate('Tabbar');
    }
  };
  /**
   * 点击重新获取按钮
   */
  reGetVerificationCode = () => {
    this.countDown();
  };
  /**
   * 开启获取验证码的定时器
   */
  countDown = () => {
    console.log('开启倒计时');
    if (this.state.isCountDowning) {
      return;
    }
    this.setState({isCountDowning: true});
    let seconds = 30;
    // 重新获取(30s)
    this.setState({btnText: `重新获取(${seconds}s)`});
    let timeId = setInterval(() => {
      seconds--;
      this.setState({btnText: `重新获取(${seconds}s)`});
      if (seconds === 0) {
        clearInterval(timeId);
        this.setState({btnText: '重新获取', isCountDowning: false});
      }
    }, 1000);
  };
  skipToTabbar = () => {
    this.props.navigation.navigate('Tabbar');
  };
  /**
   * 渲染登录界面
   * @returns
   */
  renderLogin = () => {
    const {mobile, phoneValid} = this.state;
    return (
      <View>
        <View
          style={{
            marginTop: pxToDp(100),
            width: pxToDp(316),
            alignSelf: 'center',
          }}>
          <Input
            leftIcon={{
              type: 'FontAwesome',
              name: 'phone',
              color: '#ccc',
              size: pxToDp(36),
            }}
            placeholder="请输入手机号"
            maxLength={11}
            keyboardType="phone-pad"
            value={mobile}
            inputStyle={{
              color: '#333',
              textAlign: 'center',
              fontSize: pxToDp(36),
            }}
            onChangeText={this.mobileChangeText}
            errorMessage={phoneValid ? '' : '手机号格式不正确'}
            onSubmitEditing={this.mobileSubmitEditing}
          />
        </View>
        <View style={{marginTop: pxToDp(180)}}>
          {/* 2.1.3 GradientButton Start */}
          <GradientButton
            onPress={this.mobileSubmitEditing}
            style={{
              width: pxToDp(80),
              alignSelf: 'center',
              height: pxToDp(80),
              borderRadius: pxToDp(80),
            }}
            textStyle={{fontSize: pxToDp(32)}}>
            ✔
          </GradientButton>
          {/* 2.1.3 GradientButton End */}
        </View>
      </View>
    );
  };
  /**
   * 渲染获取验证码界面
   * @returns
   */
  renderVerificationCode = () => {
    const {mobile, verificationCode, btnText, isCountDowning} = this.state;
    return (
      <View>
        <View>
          <Text
            style={{fontSize: pxToDp(24), color: '#888', fontWeight: 'bold'}}>
            输入6位验证码
          </Text>
        </View>
        <View style={{marginTop: pxToDp(12)}}>
          <Text style={{color: '#888'}}>已发到：+86 {mobile}</Text>
        </View>
        <View>
          <CodeField
            value={verificationCode}
            onChangeText={this.onVerificationCodeChangeText}
            onSubmitEditing={this.onVerificationCodeSubmitEditing}
            cellCount={6}
            rootStyle={styles.codeFiledRoot}
            keyboardType="number-pad"
            renderCell={({index, symbol, isFocused}) => (
              <Text
                key={index}
                style={[styles.cell, isFocused && styles.focusCell]}>
                {symbol || (isFocused ? <Cursor /> : null)}
              </Text>
            )}
          />
        </View>
        <View style={{marginTop: pxToDp(96)}}>
          <GradientButton
            disabled={isCountDowning}
            onPress={this.reGetVerificationCode}
            style={{
              width: '50%',
              alignSelf: 'center',
              height: pxToDp(60),
              borderRadius: pxToDp(60),
            }}>
            {btnText}
          </GradientButton>
        </View>
      </View>
    );
  };
  /**
   * 渲染主界面
   * @returns
   */
  render() {
    const {showLogin} = this.state;
    return (
      <View>
        {/* 0.0 StatusBar Start */}
        <StatusBar backgroundColor="transparent" translucent={true} />
        {/* 0.0 StatusBar End */}
        {/* 1.0 title Start */}
        <View
          style={{
            width: pxToDp(216),
            height: pxToDp(108),
            flexDirection: 'row',
            alignSelf: 'center',
            marginTop: '30%',
            justifyContent: 'center',
            alignItems: 'center',
          }}>
          <IconFont
            name="iconLogo"
            style={{
              marginRight: pxToDp(12),
              fontSize: pxToDp(72),
              color: '#36cfc9',
            }}
          />
          {/*<Image*/}
          {/*  style={{*/}
          {/*    width: pxToDp(108),*/}
          {/*    height: pxToDp(108),*/}
          {/*  }}*/}
          {/*  source={require('../../../res/yizhi.png')}*/}
          {/*/>*/}
          <View
            style={{
              width: pxToDp(108),
              height: pxToDp(108),
              justifyContent: 'center',
            }}>
            <Text
              style={{
                fontSize: pxToDp(25),
                color: '#888',
                fontWeight: 'bold',
                alignSelf: 'center',
              }}>
              作文纸条
            </Text>
          </View>
        </View>
        {/* 1.0 title End */}
        <View style={{padding: pxToDp(24)}}>
          {/* 2.0 login Start */}
          {showLogin ? this.renderLogin() : this.renderVerificationCode()}

          {/* 2.0 login End */}
        </View>
        <TouchableOpacity
          onPress={this.skipToTabbar}
          style={{
            position: 'absolute',
            alignSelf: 'flex-end',
            paddingTop: pxToDp(64),
            paddingRight: pxToDp(36),
          }}>
          <Text style={{fontSize: pxToDp(18), color: '#666'}}>跳过</Text>
        </TouchableOpacity>
      </View>
    );
  }
}
const styles = StyleSheet.create({
  root: {flex: 1, padding: 20},
  title: {textAlign: 'center', fontSize: 30},
  codeFiledRoot: {marginTop: 20},
  cell: {
    width: 40,
    height: 40,
    lineHeight: 38,
    fontSize: 24,
    borderBottomWidth: 2,
    borderColor: '#00000030',
    textAlign: 'center',
    color: '#39DBD5',
  },
  focusCell: {
    borderColor: '#39DBD5',
  },
});
export default Index;
