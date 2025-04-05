import React, {Component} from 'react';
import {
  StyleSheet,
  View,
  Text,
  Image,
  TouchableOpacity,
  Modal,
  ScrollView,
  TextInput,
} from 'react-native';

import GradientButton from '@src/component/GradientButton';

import GradientNavgation from '@src/component/GradientNavgation';
import Request from '@src/util/Request';
import {
  POST_USER,
  USERS_QUERY_USER_INFO,
  USERS_FOLLOW_FRIENDID,
  ARTICLE_RID,
  POST_ID_TEXT,
  COMMENT_PUBLISH_ID,
  COMMENT_ID_LIKE,
} from '@src/util/Api';
import HeaderImageScrollView from 'react-native-image-header-scroll-view';
import {Carousel} from 'teaset';
import {pxToDp} from '@src/util/pxToDp';
import Date from '@src/util/Date';
import IconFont from '@src/component/IconFont';
import LinearGradient from 'react-native-linear-gradient';
import ImageViewer from 'react-native-image-zoom-viewer';
import Validator from '@src/util/Validator';
import {inject, observer} from 'mobx-react';
import TouchableScale from 'react-native-touchable-scale';
import Toast from '@src/util/Toast';
class Index extends Component {
  params = {
    page: 1,
    pagesize: 5,
  };
  totalPages = 2;
  isLoading = false;
  state = {
    text: '',
    list: [],
    showInput: true,
  };
  componentDidMount() {
    this.getList();
  }
  // 渲染富文本内容
  rendeRichText = text => {
    const list = Validator.renderRichText(text);
    return list.map((v, i) => {
      if (v.text) {
        return (
          <Text style={{color: '#666'}} key={i}>
            {v.text}
          </Text>
        );
      } else if (v.image) {
        return (
          <Image
            style={{width: pxToDp(25), height: pxToDp(25)}}
            key={i}
            source={EMOTIONS_DATA[v.image]}
          />
        );
      } else {
        return <></>;
      }
    });
  };

  // 获取评论列表
  getList = async (isNew = false) => {
    const url = COMMENT_PUBLISH_ID.replace(
      ':publishId',
      this.props.route.params.id.toString(),
    );
    console.log(this.props.route.params.id);
    const res = await Request.privateGet(url, this.params);
    console.log(res);
    this.totalPages = res.data.page;
    this.isLoading = false;
    if (isNew) {
      this.setState({list: res.data.records});
    } else {
      this.setState({
        list: [...this.state.list, ...res.data.records],
      });
    }
  };

  // 给评论点赞
  handleSetStar = async id => {
    const url = COMMENT_ID_LIKE.replace(':id', id);
    const res = await Request.privateGet(url);
    console.log(res);
    Toast.smile('点赞成功');
    this.params.page = 1;
    this.getList(true);
  };

  // 结束输入
  handleEditingEnd = () => {
    this.setState({showInput: false, text: ''});
  };

  // 完成编辑 发送评论
  handleSubmit = async () => {
    const {text} = this.state;
    if (!text.trim()) {
      Toast.smile('评论不能为空');
      return;
    }
    // tid 动态的id
    const url = POST_ID_TEXT.replace(
      ':id',
      this.props.route.params.id.toString(),
    );
    const res = await Request.privatePost(url, {text: text});
    console.log(res);
    this.handleEditingEnd();
    if (res.ok) {
      Toast.smile('评论成功');

      this.params.page = 1;
      this.getList(true);
    }
  };

  // 滚动分页
  onScroll = ({nativeEvent}) => {
    /* 
    1 滚动条触底事件 | 还有没有下一页数据 | 节流 
     */

    const isReachBottom =
      nativeEvent.contentSize.height -
        nativeEvent.layoutMeasurement.height -
        nativeEvent.contentOffset.y <
      10;
    const hasMore = this.params.page < this.totalPages;
    if (isReachBottom && hasMore && !this.isLoading) {
      this.isLoading = true;
      this.params.page++;
      this.getList();
    }
  };
  render() {
    const item = this.props.route.params;
    const {list, counts, showInput, text} = this.state;
    return (
      <>
        <GradientNavgation title="创作详情" />
        <ScrollView
          onScroll={this.onScroll}
          style={{flex: 1, backgroundColor: '#eee'}}>
          {/* 1.0 用户信息 开始 */}
          <View
            style={{
              padding: pxToDp(12),
              borderRadius: pxToDp(18),
              marginBottom: pxToDp(4),
              marginTop: pxToDp(8),
              marginLeft: pxToDp(8),
              marginRight: pxToDp(8),
              backgroundColor: '#ffffff',
            }}>
            {/* 2.2.1 用户信息 开始 */}
            <View style={{flexDirection: 'row', alignItems: 'center'}}>
              <View style={{paddingRight: pxToDp(15)}}>
                <Image
                  style={{
                    width: pxToDp(40),
                    height: pxToDp(40),
                    borderRadius: pxToDp(20),
                  }}
                  source={{uri: item.logo}}
                />
              </View>

              <View style={{flex: 2, justifyContent: 'space-around'}}>
                <View style={{flexDirection: 'row', alignItems: 'center'}}>
                  <Text style={{color: '#555'}}>{item.nickName}</Text>
                  <IconFont
                    style={{
                      marginLeft: pxToDp(5),
                      marginRight: pxToDp(5),
                      fontSize: pxToDp(18),
                      color: item.sex === 'woman' ? '#36cfc9' : '#37DC8A',
                    }}
                    name={
                      item.sex === 'woman' ? 'iconWomanMini' : 'iconManMini'
                    }
                  />
                  <Text style={{color: '#555'}}>{item.age}岁</Text>
                </View>
                <View style={{flexDirection: 'row'}}>
                  <Text style={{color: '#555', marginRight: pxToDp(5)}}>
                    {item.tags[0]}
                  </Text>
                  <Text style={{color: '#555', marginRight: pxToDp(5)}}>|</Text>
                  <Text style={{color: '#555', marginRight: pxToDp(5)}}>
                    {item.tags[1]}
                  </Text>
                  <Text style={{color: '#555', marginRight: pxToDp(5)}}>|</Text>
                  <Text style={{color: '#555', marginRight: pxToDp(5)}}>
                    {item.tags[2]}
                  </Text>
                </View>
              </View>
              <View
                style={{
                  flexDirection: 'row',
                  alignSelf: 'flex-end',
                  paddingTop: pxToDp(5),
                  paddingBottom: pxToDp(5),
                }}>
                <View>
                  <Text style={{color: '#999'}}>距离 {item.distance}</Text>
                </View>
                <View>
                  <Text style={{color: '#999', marginLeft: pxToDp(8)}}>
                    {Date(item.created).fromNow()}
                  </Text>
                </View>
              </View>
            </View>
            {/* 2.2.1 用户信息 结束 */}

            {/* 2.3 创作内容 开始 */}
            {/* <View>
                  <Text
                    style={{
                      alignSelf: 'center',
                      fontSize: pxToDp(18),
                      fontWeight: 'bold',
                      color: '#333',
                      marginTop: pxToDp(10),
                    }}>
                    {item.title}
                  </Text>
                </View> */}
            <View
              style={{
                marginTop: pxToDp(8),
                flexDirection: 'row',
                flexWrap: 'wrap',
                alignItems: 'center',
              }}>
              {this.rendeRichText(item.text)}
            </View>
            {/* 2.3 创作内容 结束 */}
            {/* 2.4 相册 开始 */}
            <View
              style={{
                flexWrap: 'wrap',
                flexDirection: 'row',
                paddingTop: pxToDp(5),
                paddingBottom: pxToDp(5),
              }}>
              {item.medias.map((vv, ii) => (
                <TouchableOpacity
                  onPress={() => this.handleShowAlbum(index, ii)}
                  key={ii}>
                  <Image
                    style={{
                      width: pxToDp(70),
                      height: pxToDp(70),
                      marginRight: pxToDp(5),
                    }}
                    source={{uri: vv}}
                  />
                </TouchableOpacity>
              ))}
            </View>
            {/* 2.4 相册 结束 */}
            {/* 2.6 最新评论 发表评论 开始 */}
            <View
              style={{flexDirection: 'row', justifyContent: 'space-between'}}>
              <View style={{flexDirection: 'row', alignItems: 'center'}}>
                <Text style={{color: '#666'}}>最新评论</Text>
                <View
                  style={{
                    backgroundColor: '#36cfc9',
                    height: pxToDp(20),
                    width: pxToDp(20),
                    borderRadius: pxToDp(10),
                    marginLeft: pxToDp(5),
                    alignItems: 'center',
                    justifyContent: 'center',
                  }}>
                  <Text style={{color: '#fff'}}>{list.length}</Text>
                </View>
              </View>
              <View>
                <GradientButton
                  onPress={() => this.setState({showInput: true})}
                  textStyle={{fontSize: pxToDp(12)}}
                  style={{
                    width: pxToDp(80),
                    height: pxToDp(36),
                    borderRadius: pxToDp(18),
                  }}>
                  发表评论
                </GradientButton>
              </View>
            </View>
            {/* 2.6 最新评论 发表评论 结束 */}
            {/* 2.7 评论列表 开始 */}
            <View>
              {list.map((v, i) => (
                <View
                  key={i}
                  style={{
                    flexDirection: 'row',
                    paddingTop: pxToDp(5),
                    paddingBottom: pxToDp(5),
                    borderBottomColor: '#ccc',
                    borderBottomWidth: pxToDp(0.5),
                    alignItems: 'center',
                  }}>
                  <View style={{paddingRight: pxToDp(10)}}>
                    <Image
                      style={{
                        width: pxToDp(40),
                        height: pxToDp(40),
                        borderRadius: pxToDp(20),
                      }}
                      source={{uri: v.logo}}
                    />
                  </View>
                  <View>
                    <Text style={{color: '#666'}}>{v.nickName}</Text>
                    <Text
                      style={{
                        color: '#666',
                        fontSize: pxToDp(13),
                        marginTop: pxToDp(5),
                        marginBottom: pxToDp(5),
                      }}>
                      {Date(v.created).format('YYYY-MM-DD HH:mm:ss')}
                    </Text>
                    <Text>{v.text}</Text>
                  </View>
                  <TouchableOpacity
                    onPress={this.handleSetStar.bind(this, v.id)}
                    style={{
                      flexDirection: 'row',
                      flex: 1,
                      justifyContent: 'flex-end',
                      alignItems: 'center',
                    }}>
                    <IconFont
                      style={{color: '#666', fontSize: pxToDp(24)}}
                      name="iconLike"
                    />
                    <Text style={{color: '#666'}}>{' ' + v.likeCount}</Text>
                  </TouchableOpacity>
                </View>
              ))}
            </View>
            {/* 2.7 评论列表 结束 */}
            <Modal
              visible={showInput}
              transparent={true}
              animationType="slide"
              onRequestClose={this.handleEditingEnd}>
              <TouchableOpacity
                onPress={this.handleEditingEnd}
                style={{
                  flex: 1,
                  backgroundColor: 'rgba(0,0,0,0.5)',
                  position: 'relative',
                }}>
                <View
                  style={{
                    backgroundColor: '#eee',
                    flexDirection: 'row',
                    alignItems: 'center',
                    position: 'absolute',
                    width: '100%',
                    left: 0,
                    bottom: 0,
                    padding: pxToDp(10),
                  }}>
                  <TextInput
                    autoFocus
                    value={text}
                    onChangeText={t => this.setState({text: t})}
                    onSubmitEditing={this.handleSubmit}
                    placeholder="发表评论"
                    style={{
                      backgroundColor: '#fff',
                      flex: 5,
                      borderRadius: pxToDp(18),
                      height: pxToDp(40),
                    }}
                  />
                  <Text
                    onPress={this.handleSubmit}
                    style={{flex: 1, textAlign: 'center', color: '#666'}}>
                    发送
                  </Text>
                </View>
              </TouchableOpacity>
            </Modal>
          </View>
          {/* 1.0 用户信息 结束 */}
          {this.params.page >= this.totalPages ? (
            <View
              style={{
                height: pxToDp(40),
                alignItems: 'center',
                justifyContent: 'center',
                backgroundColor: '#ccc',
              }}>
              <Text>没有更多数据</Text>
            </View>
          ) : (
            <></>
          )}
        </ScrollView>
      </>
    );
  }
}
export default Index;
