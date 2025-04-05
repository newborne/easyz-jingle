import React, {Component} from 'react';
import {
  StyleSheet,
  View,
  Text,
  Image,
  TouchableOpacity,
  Modal,
} from 'react-native';
import Request from '@src/util/Request';
import {
  POST_USER,
  USERS_QUERY_USER_INFO,
  USERS_FOLLOW_FRIENDID,
  MATERIAL_RID,
  MATERIAL_ID_LOVE,
} from '@src/util/Api';
import HeaderImageScrollView from 'react-native-image-header-scroll-view';
import {Carousel} from 'teaset';
import {pxToDp} from '@src/util/pxToDp';
import IconFont from '@src/component/IconFont';
import LinearGradient from 'react-native-linear-gradient';
import ImageViewer from 'react-native-image-zoom-viewer';
import GradientNavgation from '@src/component/GradientNavgation';

import {inject, observer} from 'mobx-react';
import TouchableScale from 'react-native-touchable-scale';
import Toast from '@src/util/Toast';

@inject('UserStore')
@observer
class Index extends Component {
  state = {
    materialDetail: {},
  };
  params = {
    page: 1,
    pagesize: 10,
  };
  // 总页数
  totalPages = 1;
  // 当前是否有请求在发送中
  isLoading = false;

  componentDidMount() {
    this.getDetail();
  }

  // 获取朋友详情
  getDetail = async () => {
    console.log(this.props.route.params.materialRid);
    const url1 = MATERIAL_RID.replace(
      ':materialRid',
      this.props.route.params.materialRid,
    );
    const res1 = await Request.privateGet(url1);

    console.log(JSON.stringify(res1));

    this.setState({
      materialDetail: res1.data,
    });
  };

  // 关注
  follow = async () => {
    const url = USERS_FOLLOW_FRIENDID.replace(
      ':friendId',
      this.props.route.params.id,
    );
    const res = await Request.privateGet(url);
    if (res.ok) {
      Toast.smile('成功关注', 1000, 'center');
    } else {
      Toast.smile('已关注', 1000, 'center');
    }
  };

  handLove = async materialDetail => {
    const url = MATERIAL_ID_LOVE.replace(':id', materialDetail.id);
    const res = await Request.privateGet(url);
    // console.log(res);
    if (!res.ok) {
      // 取消喜欢
      Toast.smile('取消喜欢');
    } else {
      // 喜欢成功
      Toast.smile('喜欢成功');
    }

    this.params.page = 1;
    this.getDetail();
  };

  render() {
    // console.log(this.props.route.params);
    const {materialDetail, imgUrls, currentIndex, showAlbum, records} =
      this.state;
    if (!materialDetail) return <></>;
    return (
      <>
        <GradientNavgation title="素材详情" />

        <View style={styles.card}>
          <Image
            source={require('@src/res/image/k.png')}
            style={{width: pxToDp(72), height: pxToDp(72)}}
          />
          <View
            style={{
              height: pxToDp(24),
              position: 'absolute',
              marginTop: pxToDp(24),
              backgroundColor: '#36cfc9',
              justifyContent: 'center',
              alignSelf: 'center',
              borderRadius: pxToDp(12),
            }}>
            <Text
              style={{
                marginLeft: pxToDp(16),
                marginRight: pxToDp(16),
                color: '#fff',
                fontSize: pxToDp(12),
                textAlign: 'center',
              }}>
              {materialDetail.tags}
            </Text>
          </View>
          <View
            style={{
              flex: 1,
              alignItems: 'center',
              justifyContent: 'space-around',
            }}>
            <View style={{flexDirection: 'row', alignItems: 'center'}}>
              <Text style={{color: '#555'}}>{materialDetail.text}</Text>
            </View>
          </View>
        </View>
        <View
          style={{
            flexDirection: 'row',
            justifyContent: 'center',
            width: '60%',
            alignSelf: 'center',
          }}>
          <TouchableScale
            onPress={this.handLove.bind(this, materialDetail)}
            style={{
              backgroundColor: '#ff5314',
              width: pxToDp(60),
              height: pxToDp(60),
              borderRadius: pxToDp(30),
              alignItems: 'center',
              justifyContent: 'center',
            }}>
            <IconFont
              style={{fontSize: pxToDp(30), color: '#fff'}}
              name="iconLove"
            />
            <Text style={{fontSize: pxToDp(12), color: '#fff'}}>
              {materialDetail.loveCount}
            </Text>
          </TouchableScale>
        </View>
      </>
    );
  }
}

const styles = StyleSheet.create({
  card: {
    height: '50%',
    borderRadius: 24,
    borderWidth: 2,
    borderColor: '#eee',
    backgroundColor: '#fff',
    margin: pxToDp(24),
    paddingLeft: pxToDp(12),
    paddingRight: pxToDp(12),
    paddingBottom: pxToDp(24),
  },
  text: {
    textAlign: 'center',
    fontSize: 50,
    backgroundColor: 'transparent',
  },
});

export default Index;
