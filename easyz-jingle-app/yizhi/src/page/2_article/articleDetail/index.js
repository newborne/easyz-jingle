import React, {Component} from 'react';
import {
  StyleSheet,
  View,
  Text,
  Image,
  TouchableOpacity,
  Modal,
  ScrollView,
} from 'react-native';
import Request from '@src/util/Request';
import {
  POST_USER,
  USERS_QUERY_USER_INFO,
  USERS_FOLLOW_FRIENDID,
  ARTICLE_RID,
  ARTICLE_ID_LIKE,
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
    articleDetail: {},
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

  // 获取详情
  getDetail = async () => {
    console.log(this.props.route.params.articleRid);
    const url1 = ARTICLE_RID.replace(
      ':articleRid',
      this.props.route.params.articleRid,
    );
    const res1 = await Request.privateGet(url1);

    console.log(JSON.stringify(res1));

    this.setState({
      articleDetail: res1.data,
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

  // 点赞
  handLike = async articleDetail => {
    const url = ARTICLE_ID_LIKE.replace(':id', articleDetail.id);
    const res = await Request.privateGet(url);
    console.log(res);
    // 点赞成功 还是 取消点赞
    if (!res.ok) {
      // 取消点赞
      Toast.smile('取消成功');
    } else {
      // 点赞成功
      Toast.smile('点赞成功');
    }

    // 重新发送请求 获取数据
    // this.setState({ list: [] });
    this.params.page = 1;
    this.getDetail();
  };

  render() {
    // console.log(this.props.route.params);
    const {articleDetail, imgUrls, currentIndex, showAlbum, records} =
      this.state;
    if (!articleDetail) return <></>;
    return (
      <>
        <GradientNavgation title="作文详情" />

        <ScrollView style={styles.card}>
          <Image
            source={require('@src/res/image/n.png')}
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
              {articleDetail.tags}
            </Text>
          </View>
          <View
            style={{
              flex: 1,
              alignItems: 'center',
              justifyContent: 'space-around',
            }}>
            <View
              style={{
                flexDirection: 'row',
                alignItems: 'center',
              }}>
              <Text style={{color: '#555'}}></Text>
            </View>
            <View style={{flexDirection: 'row', alignItems: 'center'}}>
              <Text
                style={{
                  color: '#555',
                  fontSize: pxToDp(18),
                  fontWeight: 'bold',
                }}>
                {articleDetail.title + '\n'}
              </Text>
            </View>
            <View style={{flexDirection: 'row', alignItems: 'center'}}>
              <Text style={{color: '#555', marginBottom: pxToDp(12)}}>
                {'\n' + articleDetail.text}
              </Text>
            </View>
          </View>
        </ScrollView>
        <View
          style={{
            flexDirection: 'row',
            justifyContent: 'center',
            width: '60%',
            alignSelf: 'center',
          }}>
          <TouchableScale
            onPress={this.handLike.bind(this, articleDetail)}
            style={{
              backgroundColor: '#ff5314',
              width: pxToDp(60),
              height: pxToDp(60),
              marginBottom: pxToDp(12),
              borderRadius: pxToDp(30),
              alignItems: 'center',
              justifyContent: 'center',
            }}>
            <IconFont
              style={{fontSize: pxToDp(30), color: '#fff'}}
              name="iconLike"
            />
            <Text
              style={{
                fontSize: pxToDp(12),
                color: '#fff',
              }}>
              {articleDetail.likeCount}
            </Text>
          </TouchableScale>
        </View>
      </>
    );
  }
}

const styles = StyleSheet.create({
  card: {
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
