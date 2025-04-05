import CityJson from '@src/res/json/citys.json';
import Picker from 'react-native-picker';
import DatePicker from 'react-native-datepicker-2021';
import date from '@src/util/Date';
import React, {Component} from 'react';
import {View, Text, Image, TextInput, TouchableOpacity} from 'react-native';
import GradientNavgation from '@src/component/GradientNavgation';
import {ListItem} from 'react-native-elements';
import {pxToDp} from '@src/util/pxToDp';
import {
  USERS_SAVE_USER_LOGO,
  USERS_UPDATE_USER_INFO,
  USERS_QUERY_USER_INFO,
} from '@src/util/Api';
import ImagePicker from 'react-native-image-crop-picker';
import Request from '@src/util/Request';
import Toast from '@src/util/Toast';
import {Overlay} from 'react-native-elements';
import IconFont from '@src/component/IconFont';
import TouchableScale from 'react-native-touchable-scale';
import LinearGradient from 'react-native-linear-gradient';
import {BackgroundImage} from 'react-native-elements/dist/config';
import {inject, observer} from 'mobx-react';

@inject('UserStore')
@observer
class Index extends Component {
  state = {
    // 是否显示 昵称输入框
    showUserName: false,
    // 是否显示 性别选择框
    showSex: false,
    user: {
      age: 12,
      sex: 'man',
      logo: 'https://z3.ax1x.com/2021/05/22/gqLnWq.png',
      userName: '奋斗的少年',
      city: '上海市',
      edu: '六年级',
    },
  };
  // 选择头像
  onPickerImage = async () => {
    // 1   获取到 选中后的图片
    const image = await ImagePicker.openPicker({
      width: 300,
      height: 400,
      cropping: true,
    });

    // 2 将本地图片上传到服务器
    const res = await this.uploadHeadImg(image);
    // res.data.headImgShortPath
    const header = res.data.headImgShortPath;

    const res2 = await this.onSubmitUser({header});
    console.log(res2);
  };

  // 完成编辑
  // user={header} ={userName}
  onSubmitUser = async user => {
    const res = await Request.privatePost(USERS_UPDATE_USER_INFO, user);
    // 1 给用户友好的提示
    Toast.smile('修改成功');
    // 2 刷新数据
    const res2 = await Request.privateGet(USERS_QUERY_USER_INFO);
    this.props.UserStore.setUser(res2.data);
    return Promise.resolve(res);
  };

  // 上传头像
  uploadHeadImg = image => {
    // 构造参数 发送到后台 完成 头像上传
    let formData = new FormData();
    formData.append('logo', {
      // 本地图片的地址
      uri: image.path,
      // 图片的类型
      type: image.mime,
      // 图片的名称 file:///store/com/pic/dsf/d343.jpg
      name: image.path.split('/').pop(),
    });
    // 执行头像上传
    return Request.privatePost(USERS_SAVE_USER_LOGO, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  };
  // 编辑昵称
  updateUserName = async e => {
    // 1 获取到输入框的文本 (1 state中声明txt变量 绑定在输入框的value值和 onChangeText )
    // 2 非受控表单的方式

    const userName = e.nativeEvent.text;
    if (!userName) {
      return;
    }

    await this.onSubmitUser({userName});
    this.setState({showUserName: false});
  };
  // 编辑生日
  updateBirthday = async birthday => {
    // console.log(birthday);
    this.onSubmitUser({birthday});
  };
  // 编辑性别
  updateSex = async sex => {
    await this.onSubmitUser({sex});
    this.setState({showSex: false});
  };
  // 显示城市选择组件
  showCityPicker = async () => {
    Picker.init({
      //  pickerData 要显示哪些数据 全国城市数据?
      pickerData: CityJson,
      // 默认选择哪个数据
      // selectedValue: ["河北", "唐山"],
      selectedValue: ['北京', '北京'],
      wheelFlex: [1, 1, 0], // 显示省和市
      pickerConfirmBtnText: '确定',
      pickerCancelBtnText: '取消',
      pickerTitleText: '选择城市',
      onPickerConfirm: this.updateCity,
    });
    Picker.show();
  };
  // 编辑城市
  updateCity = async arr => {
    const city = arr[1];
    await this.onSubmitUser({city});
  };

  // 显示学历选择框
  showEduPicker = async () => {
    Picker.init({
      pickerData: [
        '博士后',
        '博士',
        '硕士',
        '本科',
        '大专',
        '高中',
        '留学',
        '其他',
      ],
      selectedValue: ['其他'],
      wheelFlex: [1, 0, 0], // 显示省和市
      pickerConfirmBtnText: '确定',
      pickerCancelBtnText: '取消',
      pickerTitleText: '选择学历',
      onPickerConfirm: this.updateEdu,
    });
    Picker.show();
  };
  // 编辑学历
  updateEdu = async arr => {
    const edu = arr[0];
    this.onSubmitUser({edu});
  };

  render() {
    const {showUserName, showSex, user} = this.state;
    const dateNow = new Date();
    const currentDate = `${dateNow.getFullYear()}-${
      dateNow.getMonth() + 1
    }-${dateNow.getDate()}`;
    return (
      <View style={{height: '100%'}}>
        <GradientNavgation title="个人信息" />

        <Overlay
          visible={showUserName}
          onBackdropPress={() => this.setState({showUserName: false})}>
          <TextInput
            placeholder="修改昵称"
            onSubmitEditing={this.updateUserName}
            style={{width: pxToDp(200)}}
          />
        </Overlay>
        <Overlay
          visible={showSex}
          onBackdropPress={() => this.setState({showSex: false})}>
          <View
            style={{
              width: pxToDp(200),
              height: pxToDp(60),
              justifyContent: 'space-evenly',
            }}>
            <Text style={{color: '#666'}} onPress={() => this.updateSex('男')}>
              男
            </Text>
            <Text style={{color: '#666'}} onPress={() => this.updateSex('女')}>
              女
            </Text>
          </View>
        </Overlay>
        {/* 用户信息 */}
        <View
          style={{
            marginTop: pxToDp(32),
            marginBottom: pxToDp(32),
            height: pxToDp(152),
            justifyContent: 'center',
            alignSelf: 'center',
          }}>
          <View
            style={{
              width: pxToDp(72),
              height: pxToDp(110),
              justifyContent: 'center',
              flexDirection: 'row',
            }}>
            <Image
              style={{
                alignSelf: 'flex-start',
                width: pxToDp(96),
                height: pxToDp(96),
                borderRadius: pxToDp(48),
              }}
              source={{uri: user.logo}}
            />
            <View
              style={{
                width: pxToDp(36),
                height: pxToDp(36),
                borderRadius: pxToDp(18),
                backgroundColor: '#fff',
                position: 'absolute',
                alignSelf: 'flex-end',
                alignItems: 'center',
                justifyContent: 'center',
              }}>
              <TouchableOpacity onPress={this.onPickerImage}>
                <IconFont name="iconVideo" style={{fontSize: pxToDp(32)}} />
              </TouchableOpacity>
            </View>
          </View>
        </View>

        <ListItem
          Component={TouchableScale}
          containerStyle={{
            borderRadius: pxToDp(24),
            margin: pxToDp(10),
          }}
          friction={90} //
          tension={124} // These props are passed to the parent component (here TouchableScale)
          activeScale={0.95} //
          linearGradientProps={{
            colors: ['#eee', '#fff'],
            start: {x: 0, y: 0},
            end: {x: 1, y: 1},
          }}
          ViewComponent={LinearGradient} // Only if no expo
          onPress={() => this.setState({showUserName: true})}>
          <ListItem.Content
            style={{flexDirection: 'row', justifyContent: 'space-between'}}>
            <ListItem.Title style={{color: '#000', fontWeight: 'bold'}}>
              昵称
            </ListItem.Title>
            <ListItem.Title style={{color: '#000', fontWeight: 'bold'}}>
              {user.userName}
            </ListItem.Title>
          </ListItem.Content>
          <ListItem.Chevron color="#000" />
        </ListItem>
        <View style={{position: 'relative'}}>
          <ListItem
            Component={TouchableScale}
            containerStyle={{
              borderRadius: pxToDp(24),
              margin: pxToDp(10),
            }}
            friction={90} //
            tension={124} // These props are passed to the parent component (here TouchableScale)
            activeScale={0.95} //
            linearGradientProps={{
              colors: ['#eee', '#fff'],
              start: {x: 0, y: 0},
              end: {x: 1, y: 1},
            }}
            ViewComponent={LinearGradient} // Only if no expo
          >
            <ListItem.Content
              style={{flexDirection: 'row', justifyContent: 'space-between'}}>
              <ListItem.Title style={{color: '#000', fontWeight: 'bold'}}>
                生日
              </ListItem.Title>
              <ListItem.Title style={{color: '#000', fontWeight: 'bold'}}>
                {date(user.birthday).format('YYYY-MM-DD')}
              </ListItem.Title>
              <DatePicker
                androidMode="spinner"
                style={{
                  width: '100%',
                  position: 'absolute',
                  top: 0,
                  left: 0,
                  height: '100%',
                  opacity: 0,
                }}
                date={date(user.birthday).format('YYYY-MM-DD')}
                mode="date"
                placeholder="选择生日"
                format="YYYY-MM-DD"
                minDate="1900-01-01"
                maxDate={currentDate}
                confirmBtnText="确定"
                cancelBtnText="取消"
                onDateChange={this.birthdayUpdate}
              />
            </ListItem.Content>
            <ListItem.Chevron color="#000" />
          </ListItem>
        </View>
        <ListItem
          Component={TouchableScale}
          containerStyle={{
            borderRadius: pxToDp(24),
            margin: pxToDp(10),
          }}
          friction={90} //
          tension={124} // These props are passed to the parent component (here TouchableScale)
          activeScale={0.95} //
          linearGradientProps={{
            colors: ['#eee', '#fff'],
            start: {x: 0, y: 0},
            end: {x: 1, y: 1},
          }}
          ViewComponent={LinearGradient} // Only if no expo
          onPress={() => this.setState({showSex: true})}>
          <ListItem.Content
            style={{flexDirection: 'row', justifyContent: 'space-between'}}>
            <ListItem.Title style={{color: '#000', fontWeight: 'bold'}}>
              性别
            </ListItem.Title>
            <ListItem.Title style={{color: '#000', fontWeight: 'bold'}}>
              {user.sex === 'man' ? '男' : '女'}
            </ListItem.Title>
          </ListItem.Content>
          <ListItem.Chevron color="#000" />
        </ListItem>
        <ListItem
          Component={TouchableScale}
          containerStyle={{
            borderRadius: pxToDp(24),
            margin: pxToDp(10),
          }}
          friction={90} //
          tension={124} // These props are passed to the parent component (here TouchableScale)
          activeScale={0.95} //
          linearGradientProps={{
            colors: ['#eee', '#fff'],
            start: {x: 0, y: 0},
            end: {x: 1, y: 1},
          }}
          ViewComponent={LinearGradient} // Only if no expo
          //   onPress={this.showCityPicker}
        >
          <ListItem.Content
            style={{flexDirection: 'row', justifyContent: 'space-between'}}>
            <ListItem.Title style={{color: '#000', fontWeight: 'bold'}}>
              现居城市
            </ListItem.Title>
            <ListItem.Title style={{color: '#000', fontWeight: 'bold'}}>
              {user.city}
            </ListItem.Title>
          </ListItem.Content>
          <ListItem.Chevron color="#000" />
        </ListItem>
        <ListItem
          Component={TouchableScale}
          containerStyle={{
            borderRadius: pxToDp(24),
            margin: pxToDp(10),
          }}
          friction={90} //
          tension={124} // These props are passed to the parent component (here TouchableScale)
          activeScale={0.95} //
          linearGradientProps={{
            colors: ['#eee', '#fff'],
            start: {x: 0, y: 0},
            end: {x: 1, y: 1},
          }}
          ViewComponent={LinearGradient} // Only if no expo
          //   onPress={this.showEduPicker}
        >
          <ListItem.Content
            style={{flexDirection: 'row', justifyContent: 'space-between'}}>
            <ListItem.Title style={{color: '#000', fontWeight: 'bold'}}>
              学历
            </ListItem.Title>
            <ListItem.Title style={{color: '#000', fontWeight: 'bold'}}>
              {user.edu}
            </ListItem.Title>
          </ListItem.Content>
          <ListItem.Chevron color="#000" />
        </ListItem>
      </View>
    );
  }
}
export default Index;
