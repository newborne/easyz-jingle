import React, {Component} from 'react';
import {Image, Text, TextInput, TouchableOpacity, View} from 'react-native';
import ImagePicker from 'react-native-image-crop-picker';
import DatePicker from 'react-native-datepicker-2021';
import Picker from 'react-native-picker';
import {inject, observer} from 'mobx-react';
import {LOGIN_SAVE_USER_INFO, LOGIN_SAVE_USER_LOGO} from '@src/util/Api';
import Request from '@src/util/Request';
import {pxToDp} from '@src/util/pxToDp';
import Toast from '@src/util/Toast';
import CitysJson from '@src/res/json/citys.json';
// import Geo from '@src/util/Geo';
import GradientButton from '@src/component/GradientButton';
import IconFont from '@src/component/IconFont';
import {Overlay} from 'teaset';
// import {Overlay} from '@src/node_modules_plus/teaset';

@inject('RootStore')
@observer
class Index extends Component {
  state = {
    // 昵称
    userName: '',
    // 性别
    sex: 'man',
    // 生日
    birthday: '',
    // 城市
    city: '',
  };
  /**
   * 组件挂载
   */
  async componentDidMount() {
    // //根组件中已初始化
    // const res = await Geo.getCityByLocation();
    // console.log(res);
    // const city = res.regeocode.addressComponent.province.replace('市', '');
    // this.setState({city});
  }
  /**
   * 选择性别
   * @param {string} sex
   */
  chooeseSex = sex => {
    this.setState({sex});
  };
  /**
   * 选择城市
   */
  showCityPicker = () => {
    Picker.init({
      //  pickerData 要显示哪些数据 全国城市数据?
      pickerData: CitysJson,
      // 默认选择哪个数据
      selectedValue: ['北京', '北京'],
      wheelFlex: [1, 1, 0], // 显示省和市
      pickerConfirmBtnText: '确定',
      pickerCancelBtnText: '取消',
      pickerTitleText: '选择城市',
      onPickerConfirm: data => {
        this.setState({
          city: data[1],
        });
      },
    });
    Picker.show();
  };
  /**
   * 选择头像按钮
   */
  chooeseLogo = async () => {
    const {userName, birthday, city} = this.state;
    if (!userName) {
      Toast.sad('未输入昵称', 2400, 'center');
      return;
    } else if (!birthday) {
      Toast.sad('未选择生日', 2400, 'center');
      return;
    } else if (!city) {
      Toast.sad('未定位到城市', 2400, 'center');
      return;
    }
    // 获取到 选中后的图片
    const image = await ImagePicker.openPicker({
      width: 400,
      height: 400,
      cropping: true,
    });
    console.log(image);
    let overlayViewRef = null;
    // 显示审核中 效果
    let overlayView = (
      <Overlay.View
        style={{
          flex: 1,
          backgroundColor: '#333',
          opacity: 0.85,
          justifyContent: 'center',
        }}
        modal={true}
        overlayOpacity={0}
        ref={v => (overlayViewRef = v)}>
        <View
          style={{
            alignSelf: 'center',
            alignItems: 'center',
            justifyContent: 'center',
            width: pxToDp(300),
            height: pxToDp(300),
            borderWidth: pxToDp(2),
            borderColor: '#36CFC9',
            borderRadius: pxToDp(1),
            position: 'relative',
          }}>
          <Image
            style={{
              width: '100%',
              height: '100%',
              position: 'absolute',
              left: 0,
              top: 0,
              zIndex: 2,
            }}
            source={require('@src/res/image/checklogo.webp')}
          />
          <Image
            source={{
              uri: image.path,
            }}
            style={{
              width: pxToDp(150),
              height: pxToDp(150),
              borderWidth: pxToDp(2),
              borderColor: '#36CFC9',
              borderRadius: pxToDp(75),
              position: 'absolute',
              zIndex: 1,
            }}
          />
          <View
            style={{
              alignItems: 'flex-end',
              alignSelf: 'center',
              height: '100%',
              flexDirection: 'row',
              position: 'absolute',
              zIndex: 0,
            }}>
            <Text
              style={{
                color: '#36CFC9',
              }}>
              正在审核
            </Text>
          </View>
        </View>
      </Overlay.View>
    );
    Overlay.show(overlayView);
    // 构造参数 完善个人信息
    // state
    let params = this.state;
    console.log(params);
    const res1 = await Request.privatePost(LOGIN_SAVE_USER_INFO, params);
    if (res1.errCode === '000004') {
      // 完善信息失败
      console.log(res1);
      overlayViewRef.close();
      Toast.sad(res1.errMessage, 2400, 'center');
      return;
    }
    // 上传头像
    const res2 = await this.uploadLogo(image);
    if (res2.errCode === '000003') {
      //失败
      console.log(res2);
      overlayViewRef.close();
      Toast.sad(res2.errMessage, 2400, 'center');
      return;
    }

    overlayViewRef.close();

    Toast.smile('审核通过', 2400, 'center');

    setTimeout(() => {
      // this.props.navigation.navigate("Tabbar");
      // this.props.navigation.reset({
      //   routes: [{ name: "Tabbar" }]
      // })
      alert('跳转界面');
      this.props.navigation.navigate('Tabbar');
    }, 2400);
  };
  /**
   * 上传头像
   * @param {string} image
   * @returns
   */
  uploadLogo = image => {
    // 构造参数 发送到后台 完成 头像上传
    let formData = new FormData();
    formData.append('logo', {
      // 本地图片的地址
      uri: image.path,
      // 图片的类型
      type: image.mime,
      // 图片的名称
      name: image.path.split('/').pop(),
    });
    // 执行头像上传
    return Request.privatePost(LOGIN_SAVE_USER_LOGO, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  };
  /**
   * 渲染主界面
   * @returns
   */
  render() {
    const {sex, userName, birthday, city} = this.state;
    const dateNow = new Date();
    const currentDate = `${dateNow.getFullYear()}-${
      dateNow.getMonth() + 1
    }-${dateNow.getDate()}`;
    return (
      <View style={{backgroundColor: '#fff', flex: 1, paddingTop: pxToDp(48)}}>
        {/* 1.0 标题 开始 */}
        <View
          style={{
            alignSelf: 'center',
          }}>
          <Text
            style={{fontSize: pxToDp(24), color: '#666', fontWeight: 'bold'}}>
            个人信息
          </Text>
        </View>

        {/* 1.0 标题 结束 */}
        {/* 2.0 性别 开始 */}
        <View
          style={{
            marginTop: pxToDp(48),
            justifyContent: 'space-around',
            width: pxToDp(216),
            alignSelf: 'center',
            flexDirection: 'row',
          }}>
          <View alignItems={'center'}>
            <TouchableOpacity
              onPress={this.chooeseSex.bind(this, 'man')}
              style={{
                width: pxToDp(72),
                height: pxToDp(72),
                borderRadius: pxToDp(36),
                backgroundColor: sex === 'man' ? '#37DC8A' : '#eee',
                justifyContent: 'center',
                alignItems: 'center',
              }}>
              <IconFont name="iconMan" style={{fontSize: pxToDp(36)}} />
            </TouchableOpacity>
            <Text style={{marginTop: pxToDp(4)}}>男</Text>
          </View>

          <View alignItems={'center'}>
            <TouchableOpacity
              onPress={this.chooeseSex.bind(this, 'woman')}
              style={{
                width: pxToDp(72),
                height: pxToDp(72),
                borderRadius: pxToDp(36),
                backgroundColor: sex === 'woman' ? '#37DC8A' : '#eee',
                justifyContent: 'center',
                alignItems: 'center',
              }}>
              <IconFont name="iconWoman" style={{fontSize: pxToDp(36)}} />
            </TouchableOpacity>
            <Text style={{marginTop: pxToDp(4)}}>女</Text>
          </View>
        </View>
        {/* 2.0 性别 结束 */}
        {/* 3.0 昵称 开始 */}
        <View
          style={{
            width: pxToDp(324),
            alignSelf: 'center',
            marginTop: pxToDp(48),
            flexDirection: 'row',
            justifyContent: 'space-between',
          }}>
          <View style={{alignSelf: 'center'}}>
            <IconFont name="iconUserName" style={{fontSize: pxToDp(24)}} />
          </View>
          <View>
            <TextInput
              value={userName}
              placeholder="输入昵称"
              placeholderTextColor="#888"
              style={{
                borderWidth: 1,
                borderColor: '#f2f2f2',
                borderRadius: pxToDp(60),
                height: pxToDp(49),
                backgroundColor: '#f2f2f2',
                color: '#333',
                fontSize: pxToDp(16),
                width: pxToDp(280),
                textAlign: 'center',
              }}
              onChangeText={userName => this.setState({userName})}
            />
          </View>
        </View>
        {/* 3.0 昵称 结束 */}
        {/* 4.0 日期 开始 */}
        <View
          style={{
            width: pxToDp(324),
            alignSelf: 'center',
            flexDirection: 'row',
            marginTop: pxToDp(24),
            justifyContent: 'space-between',
          }}>
          <View style={{alignSelf: 'center'}}>
            <IconFont name="iconBirthday" style={{fontSize: pxToDp(24)}} />
          </View>
          <View>
            <DatePicker
              style={{width: pxToDp(280)}}
              androidMode="spinner"
              date={birthday}
              mode="date"
              placeholder="选择生日"
              format="YYYY-MM-DD"
              minDate="1900-01-01"
              maxDate={currentDate}
              confirmBtnText="确定"
              cancelBtnText="取消"
              customStyles={{
                dateIcon: {
                  display: 'none',
                },
                dateInput: {
                  borderWidth: 1,
                  borderColor: '#f2f2f2',
                  borderRadius: pxToDp(60),
                  height: pxToDp(49),
                  backgroundColor: '#f2f2f2',
                  alignItems: 'center',
                },
                placeholderText: {
                  fontSize: pxToDp(16),
                  color: '#888',
                },
              }}
              onDateChange={birthday => {
                this.setState({birthday});
              }}
            />
          </View>
        </View>
        {/* 4.0 日期 结束 */}
        {/* 5.0 地址 开始 */}
        <View
          style={{
            width: pxToDp(324),
            alignSelf: 'center',
            flexDirection: 'row',
            marginTop: pxToDp(24),
            justifyContent: 'space-between',
          }}>
          <View style={{alignSelf: 'center'}}>
            <IconFont name="iconMap" style={{fontSize: pxToDp(24)}} />
          </View>
          <View
            style={{
              flexDirection: 'row',
              width: pxToDp(280),
              justifyContent: 'space-between',
            }}>
            <View style={{width: pxToDp(108)}}>
              <TextInput
                style={{
                  color: '#888',
                  fontSize: pxToDp(16),
                  height: pxToDp(49),
                }}
                value="当前定位:"
                editable={false}
              />
            </View>
            <View style={{width: pxToDp(108)}}>
              <TouchableOpacity onPress={this.showCityPicker}>
                <TextInput
                  value={city}
                  style={{
                    borderWidth: 1,
                    borderColor: '#f2f2f2',
                    borderRadius: pxToDp(60),
                    height: pxToDp(49),
                    backgroundColor: '#f2f2f2',
                    color: '#333',
                    fontSize: pxToDp(16),
                    textAlign: 'center',
                  }}
                  editable={false}
                />
              </TouchableOpacity>
            </View>
          </View>
        </View>
        {/* 5.0 地址 结束 */}
        {/* 6.0 选择头像 开始 */}
        <View style={{marginTop: pxToDp(128)}}>
          <GradientButton
            onPress={this.chooeseLogo}
            style={{
              width: '50%',
              alignSelf: 'center',
              height: pxToDp(60),
              borderRadius: pxToDp(60),
            }}>
            选择头像
          </GradientButton>
        </View>
        {/* 6.0 选择头像 结束 */}
      </View>
    );
  }
}
export default Index;
