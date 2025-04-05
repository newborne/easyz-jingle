import React, {Component} from 'react';

import {
  View,
  Text,
  FlatList,
  Image,
  Modal,
  TouchableOpacity,
} from 'react-native';
import IconFont from '@src/component/IconFont';
import ImageViewer from 'react-native-image-zoom-viewer';
import LinearGradient from 'react-native-linear-gradient';
import Validator from '@src/util/Validator';
import {EMOTIONS_DATA} from '@src/component/Emotion/datasource';
import Date from '@src/util/Date';
import {ActionSheet} from 'teaset';
import {pxToDp} from '@src/util/pxToDp';
import Toast from '@src/util/Toast';
import {
  USERS_LIST,
  MATERIAL_TYPE,
  POST_RECOMMEND,
  POST_ID_LIKE,
  POST_ID_LOVE,
} from '@src/util/Api';
import {NavigationContext} from '@react-navigation/native';
import Request from '@src/util/Request';
class Index extends Component {
  static contextType = NavigationContext;
  params = {
    page: 1,
    pagesize: 3,
  };
  totalPages = 2;
  state = {
    list: [
      // "id": "622dcdc17c84e8540c1dc9a9",
      // 	"userId": 2,
      // 	"logo": "https://z3.ax1x.com/2021/05/22/gqLnWq.png",
      // 	"nickName": "yizhi_user_02",
      // 	"sex": "man",
      // 	"age": 17,
      // 	"tags": [
      // 		"高一",
      // 		"诗歌",
      // 		"敬业"
      // 	],
      // 	"text": "用户2的创作2",
      // 	"medias": [],
      // 	"distance": "0.31km",
      // 	"created": "2022-03-13 18:56:1",
      // 	"likeCount": 1,
      // 	"commentCount": 0,
      // 	"loveCount": 0,
      // 	"hasLiked": 1,
      // 	"hasLoved": 0
    ],
    showAlbum: false,
    imgUrls: [],
    currentIndex: 0,
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
  // 获取 推荐创作的数据
  getList = async (isNew = false) => {
    const res = await Request.privateGet(POST_RECOMMEND, this.params);
    console.log(res);
    if (isNew) {
      // 重置数据
      this.setState({list: res.data.records});
    } else {
      this.setState({list: [...this.state.list, ...res.data.records]});
    }
    this.totalPages = res.pages;
    this.isLoading = false;
  };
  滚动条触底事件;
  onEndReached = () => {
    /*
  1 判断还有没有下一页数据
  2 节流阀
   */
    if (this.params.page >= this.totalPages || this.isLoading) {
      return;
    } else {
      // 还有下一页数据
      this.isLoading = true;
      this.params.page++;
      this.getList();
    }
  };

  // 点赞
  handLike = async item => {
    /*
  1 构造点赞参数 发送请求
  2 返回值里 提示 点赞成功还是取消点赞
   */
    const url = POST_ID_LIKE.replace(':id', item.id);
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
    this.getList(true);
  };

  // 喜欢
  handLove = async item => {
    const url = POST_ID_LOVE.replace(':id', item.id);
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
    this.getList(true);
  };

  // 点击相册图片放大
  handleShowAlbum = (index, ii) => {
    const imgUrls = this.state.list[index].images.map(v => ({
      url: v.thum_img_path,
    }));
    this.setState({imgUrls, currentIndex: ii, showAlbum: true});
  };

  // // 跳转到评论页面
  // goComment = item => {
  //   // this.props.navigation
  //   this.context.navigate('Comment', item);
  // };
  render() {
    const {list, imgUrls, currentIndex, showAlbum} = this.state;
    return (
      <>
        <FlatList
          style={{
            backgroundColor: '#eee',
            marginBottom: pxToDp(12),
            paddingLeft: pxToDp(4),
            paddingRight: pxToDp(4),
            paddingTop: pxToDp(8),
          }}
          onEndReached={this.onEndReached}
          onEndReachedThreshold={0.1}
          data={list}
          keyExtractor={v => v.tid + ''}
          renderItem={({item, index}) => (
            <>
              <View
                key={index}
                style={{
                  padding: pxToDp(12),
                  borderRadius: pxToDp(18),
                  marginBottom: pxToDp(4),
                  marginLeft: pxToDp(4),
                  marginRight: pxToDp(4),
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
                      <Text style={{color: '#555', marginRight: pxToDp(5)}}>
                        |
                      </Text>
                      <Text style={{color: '#555', marginRight: pxToDp(5)}}>
                        {item.tags[1]}
                      </Text>
                      <Text style={{color: '#555', marginRight: pxToDp(5)}}>
                        |
                      </Text>
                      <Text style={{color: '#555', marginRight: pxToDp(5)}}>
                        {item.tags[2]}
                      </Text>
                    </View>
                  </View>
                  <View
                    style={{
                      flexDirection: 'column',
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
                {/* 2.5 距离时间 开始 */}

                {/* 2.5 距离时间 结束 */}
                {/* 2.6 3个小图标 开始 */}
                <View
                  style={{
                    marginLeft: pxToDp(5),
                    marginRight: pxToDp(5),
                    flexDirection: 'row',
                    justifyContent: 'space-between',
                  }}>
                  <TouchableOpacity
                    style={{flexDirection: 'row', alignItems: 'center'}}
                    onPress={this.handLove.bind(this, item)}>
                    <IconFont
                      style={{
                        fontSize: pxToDp(24),
                        marginLeft: pxToDp(5),
                        marginRight: pxToDp(5),
                        color: '#666',
                      }}
                      name="iconLove"
                    />
                    <Text style={{color: '#666'}}>{item.loveCount}</Text>
                  </TouchableOpacity>
                  <TouchableOpacity
                    onPress={() => this.context.navigate('Comment', item)}
                    style={{flexDirection: 'row', alignItems: 'center'}}>
                    <IconFont
                      style={{
                        fontSize: pxToDp(24),
                        marginLeft: pxToDp(5),
                        marginRight: pxToDp(5),
                        color: '#666',
                      }}
                      name="iconComment"
                    />
                    <Text style={{color: '#666'}}></Text>
                  </TouchableOpacity>
                  <TouchableOpacity
                    onPress={this.handLike.bind(this, item)}
                    style={{flexDirection: 'row', alignItems: 'center'}}>
                    <IconFont
                      style={{
                        fontSize: pxToDp(24),
                        marginLeft: pxToDp(5),
                        marginRight: pxToDp(5),
                        color: '#666',
                      }}
                      name="iconLike"
                    />
                    <Text style={{color: '#666'}}>{item.likeCount}</Text>
                  </TouchableOpacity>
                </View>
                {/* 2.6 3个小图标 结束 */}
              </View>
              {this.params.page >= this.totalPages &&
              index === list.length - 1 ? (
                <View
                  style={{
                    height: pxToDp(30),
                    alignItems: 'center',
                    justifyContent: 'center',
                  }}>
                  <Text style={{color: '#666'}}>没有数据</Text>
                </View>
              ) : (
                <></>
              )}
            </>
          )}
        />
        <Modal visible={showAlbum} transparent={true}>
          <ImageViewer
            onClick={() => this.setState({showAlbum: false})}
            imageUrls={imgUrls}
            index={currentIndex}
          />
        </Modal>
      </>
    );
  }
}

export default Index;
