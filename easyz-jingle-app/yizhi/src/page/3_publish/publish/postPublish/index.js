import React, {Component} from 'react';
import {
  View,
  Text,
  TextInput,
  Image,
  TouchableOpacity,
  ScrollView,
} from 'react-native';
import InputScrollView from 'react-native-input-scroll-view';
import * as ImagePicker from 'react-native-image-picker';
import {ActionSheet} from 'teaset';
import Emotion from '@src/component/Emotion';
import Request from '@src/util/Request';
import {pxToDp} from '@src/util/pxToDp';
import IconFont from '@src/component/IconFont';
import Geo from '@src/util/Geo';
import Toast from '@src/util/Toast';
import {POST_PUBLISH} from '@src/util/Api';
import LinearGradient from 'react-native-linear-gradient';
import TouchableScale from 'react-native-touchable-scale';

class Index extends Component {
  state = {
    text: '',
    // 经度
    longitude: '',
    // 纬度
    latitude: '',
    // 详细地址
    location: '',
    showEmotion: false,
  };
  constructor() {
    super();
    this.refInput = React.createRef();
  }

  // 设置输入框获得焦点
  handleSetInputFocus = () => {
    // console.log(this.refInput);
    if (!this.refInput.isFocused()) {
      // 设置获得焦点
      this.refInput.focus();
    }
  };

  // 获取当前定位
  getCurrentPosition = async () => {
    const res = await Geo.getCityByLocation();
    const {province, city, district, township, streetNumber} =
      res.regeocode.addressComponent;
    this.setState({
      location: province + city + district + township,
      longitude: streetNumber.location.split(',')[0],
      latitude: streetNumber.location.split(',')[1],
    });
  };

  // // 选择图片 拍摄或者选择相册中
  // handleSelectImage = () => {
  //   const options = {
  //     title: '选择图片',
  //     cancelButtonTitle: '取消',
  //     takePhotoButtonTitle: '拍照',
  //     chooseFromLibraryButtonTitle: '相册',
  //     storageOptions: {
  //       skipBackup: true,
  //       path: 'images',
  //     },
  //   };

  //   ImagePicker.launchImageLibrary(options, response => {
  //     console.log('===============');
  //     // console.log(response);
  //     const {data, ...others} = response;
  //     console.log(others);
  //     console.log('===============');

  //     if (response.didCancel) {
  //       console.log('User cancelled image picker');
  //     } else if (response.error) {
  //       console.log('ImagePicker Error: ', response.error);
  //     } else if (response.customButton) {
  //       console.log('User tapped custom button: ', response.customButton);
  //     } else {
  //       // 图片的数量不能超过9

  //       const {tmpImgList} = this.state;
  //       if (tmpImgList.length >= 9) {
  //         Toast.message('图片的数量不能超过9');
  //         return;
  //       }
  //       tmpImgList.push(response);
  //       this.setState({tmpImgList});
  //     }
  //   });
  // };

  // // 点击图片 进行删除
  // handleImageRemove = i => {
  //   const imgDelete = () => {
  //     const {tmpImgList} = this.state;
  //     tmpImgList.splice(i, 1);
  //     this.setState({tmpImgList});
  //     Toast.smile('删除成功');
  //   };
  //   const opts = [{title: '删除', onPress: imgDelete}];

  //   ActionSheet.show(opts, {title: '取消'});
  // };

  // 发创作
  submitTrend = async () => {
    /*
    1 获取用户的输入 文本内容,图片,当前位置.. 校验
    2 先将 选择到图片 上传到对应的接口 返回 图片的在线的地址
    3 将上面的数据 结合 图片 一并提交到后台 完成 创作的发布
    4 返回上一个页面  推荐页面
     */
    const {text, location, longitude, latitude, tmpImgList} = this.state;
    if (!text || !location || !longitude || !latitude) {
      Toast.message('输入不合法');
      return;
    }

    const params = {text, location, longitude, latitude};

    const res = await Request.privatePost(POST_PUBLISH, params);

    // console.log(res);
    if (res.ok) {
      Toast.smile('发布创作成功');
    }
  };

  render() {
    const {text, title, location, tmpImgList, showEmotion} = this.state;
    return (
      <InputScrollView style={{backgroundColor: '#f7f7f7'}}>
        {/*输入框*/}
        <TextInput
          placeholder="标题"
          style={{
            fontWeight: 'bold',
            fontSize: pxToDp(32),
            backgroundColor: '#fff',
            borderRadius: pxToDp(24),
            margin: pxToDp(20),
            marginBottom: 0,
            borderWidth: 2,
            borderColor: '#eee',
          }}
          selectionColor="#999"
          value={title}
          onChangeText={title => this.setState({title})}
        />
        <TextInput
          style={{
            minHeight: pxToDp(400),
            backgroundColor: '#fff',
            borderWidth: 2,
            margin: pxToDp(20),
            borderColor: '#eee',
            borderRadius: pxToDp(24),
          }}
          selectionColor="#999"
          onPress={this.handleSetInputFocus}
          ref={ref => (this.refInput = ref)}
          placeholder="请开始你的创作"
          value={text}
          onChangeText={text => this.setState({text})}
          multiline
          textAlignVertical="top"
        />

        {/*工具栏*/}
        <View
          style={{
            flexDirection: 'row',
            alignSelf: 'flex-end',
          }}>
          <TouchableOpacity
            onPress={this.getCurrentPosition}
            style={{
              flexDirection: 'row',
              alignItems: 'center',
            }}>
            <IconFont
              style={{color: '#666', fontSize: pxToDp(24)}}
              name="iconMap"
            />
            <Text
              style={{
                fontSize: pxToDp(16),
                color: '#aaa',
                marginLeft: pxToDp(5),
                marginRight: pxToDp(40),
              }}>
              {location || '位置'}
            </Text>
          </TouchableOpacity>
          {/* <TouchableOpacity
            onPress={this.handleSelectImage}
            style={{
              marginLeft: pxToDp(40),
              marginRight: pxToDp(40),
              flexDirection: 'row',
              alignItems: 'center',
            }}>
            <IconFont
              style={{fontSize: pxToDp(30), color: '#666'}}
              name="iconImage"
            />
          </TouchableOpacity> */}
        </View>
        {/*发布*/}
        <View>
          <TouchableScale
            friction={90} //
            tension={124} // These props are passed to the parent component (here TouchableScale)
            activeScale={0.95} //
            style={{
              marginTop: pxToDp(20),
              marginBottom: pxToDp(30),
              marginLeft: pxToDp(20),
              width: pxToDp(128),
              height: pxToDp(64),
              flexDirection: 'row',
            }}
            onPress={this.submitTrend}>
            <LinearGradient
              colors={['#39DBD5', '#37DC8A']}
              start={{x: 0, y: 0}}
              end={{x: 1, y: 1}}
              style={{
                width: pxToDp(64),
                height: pxToDp(64),
                borderRadius: pxToDp(32),
                alignItems: 'center',
                justifyContent: 'center',
              }}>
              <IconFont
                style={{color: '#fff', fontSize: pxToDp(36)}}
                name="iconLogo"
              />
            </LinearGradient>
            <View style={{justifyContent: 'center', marginLeft: pxToDp(12)}}>
              <Text
                style={{
                  color: '#aaa',
                  fontSize: pxToDp(16),
                }}>
                发布
              </Text>
            </View>
          </TouchableScale>
        </View>
      </InputScrollView>
    );
  }
}
export default Index;
