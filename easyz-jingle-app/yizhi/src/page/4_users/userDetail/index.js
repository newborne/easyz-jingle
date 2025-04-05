import React, {Component} from 'react';
import {View, Text, Image, TouchableOpacity, Modal} from 'react-native';
import Request from '@src/util/Request';
import {
  POST_USER,
  USERS_QUERY_USER_INFO,
  USERS_FOLLOW_FRIENDID,
} from '@src/util/Api';
import HeaderImageScrollView from 'react-native-image-header-scroll-view';
import {Carousel} from 'teaset';
import {pxToDp} from '@src/util/pxToDp';
import IconFont from '@src/component/IconFont';
import LinearGradient from 'react-native-linear-gradient';
import ImageViewer from 'react-native-image-zoom-viewer';
import GradientNavgation from '@src/component/GradientNavgation';

import {inject, observer} from 'mobx-react';
import Toast from '@src/util/Toast';

@inject('UserStore')
@observer
class Index extends Component {
  state = {
    userDetail: {},
    // 当前用户的创作数组
    records: [],
    // 控制 图片放大组件 是否显示
    showAlbum: false,
    // 放大显示的图片的索引
    currentIndex: 0,
    // 放大图片的路径数组
    imgUrls: [],
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
    console.log(this.props.route.params.id);
    const url1 = USERS_QUERY_USER_INFO.replace(
      ':userId',
      this.props.route.params.id,
    );
    const url2 = POST_USER.replace(':userId', this.props.route.params.id);
    const res1 = await Request.privateGet(url1, this.params);
    const res2 = await Request.privateGet(url2, this.params);
    this.totalPages = res2.data.page;
    console.log(JSON.stringify(res1));
    console.log(JSON.stringify(res2));
    this.isLoading = false;
    this.setState({
      userDetail: res1.data,
      records: [...this.state.records, ...res2.data.records],
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

  render() {
    // console.log(this.props.route.params);
    const {userDetail, imgUrls, currentIndex, showAlbum, records} = this.state;
    if (!userDetail) return <></>;
    return (
      <>
        <GradientNavgation title="用户详情" />

        <View style={{backgroundColor: '#fff'}}>
          {/* 1.0 用户个人信息 开始 */}
          <View
            style={{
              flexDirection: 'row',
              padding: pxToDp(5),
              borderBottomWidth: pxToDp(0.5),
              borderColor: '#ccc',
            }}>
            <View style={{padding: pxToDp(15)}}>
              <Image
                style={{
                  width: pxToDp(64),
                  height: pxToDp(64),
                  borderRadius: pxToDp(32),
                }}
                source={{uri: userDetail.logo}}
              />
            </View>
            <View style={{flex: 2, justifyContent: 'space-around'}}>
              <View style={{flexDirection: 'row', alignItems: 'center'}}>
                <Text style={{color: '#555'}}>{userDetail.nickName}</Text>
                <IconFont
                  style={{
                    marginLeft: pxToDp(5),
                    marginRight: pxToDp(5),
                    fontSize: pxToDp(18),
                    color: userDetail.sex === 'woman' ? '#36cfc9' : '#37DC8A',
                  }}
                  name={
                    userDetail.sex === 'woman' ? 'iconWomanMini' : 'iconManMini'
                  }
                />
                <Text style={{color: '#555'}}>{userDetail.age}岁</Text>
              </View>
              <View style={{flexDirection: 'row'}}>
                <Text style={{color: '#555', marginRight: pxToDp(5)}}>
                  {userDetail.industry}
                </Text>
                <Text style={{color: '#555', marginRight: pxToDp(5)}}>|</Text>
                <Text style={{color: '#555', marginRight: pxToDp(5)}}>
                  {userDetail.edu}
                </Text>
                <Text style={{color: '#555', marginRight: pxToDp(5)}}>|</Text>
                <Text style={{color: '#555', marginRight: pxToDp(5)}}>
                  {userDetail.city}
                </Text>
              </View>
              <View
                style={{
                  flexDirection: 'row',
                  position: 'absolute',
                  right: 0,
                  top: pxToDp(15),
                }}>
                <TouchableOpacity
                  style={{marginRight: pxToDp(8)}}
                  onPress={this.follow}>
                  <LinearGradient
                    colors={['#36cfc9', '#2fb4f9']}
                    start={{x: 0, y: 0}}
                    end={{x: 1, y: -0.8}}
                    style={{
                      width: pxToDp(100),
                      height: pxToDp(36),
                      borderRadius: pxToDp(24),
                      flexDirection: 'row',
                      alignItems: 'center',
                      justifyContent: 'space-evenly',
                    }}>
                    <IconFont
                      style={{color: '#fff', fontSize: pxToDp(24)}}
                      name="iconFollow"></IconFont>
                    <Text style={{color: '#fff', fontSize: pxToDp(16)}}>
                      关注
                    </Text>
                  </LinearGradient>
                </TouchableOpacity>
              </View>
            </View>
          </View>
          {/* 1.0 用户个人信息 结束 */}
          {/* 2.0 创作 开始 */}
          <View>
            {/* 2.1 标题 开始 */}
            <View
              style={{
                padding: pxToDp(10),
                flexDirection: 'row',
                justifyContent: 'space-around',
                borderBottomWidth: pxToDp(0.5),
                borderColor: '#ccc',
              }}>
              <View style={{flexDirection: 'row', alignItems: 'center'}}>
                <Text style={{color: '#666', fontSize: pxToDp(24)}}>创作</Text>
                <View
                  style={{
                    width: pxToDp(20),
                    height: pxToDp(20),
                    borderRadius: pxToDp(10),
                    backgroundColor: '#37dc8a',
                    marginLeft: pxToDp(10),
                    alignItems: 'center',
                    justifyContent: 'center',
                  }}>
                  <Text style={{color: '#fff'}}>{records.length}</Text>
                </View>
              </View>
            </View>
            {/* 2.1 标题 结束 */}
            {/* 2.2 列表 开始 */}
            <View>
              {records.map((v, i) => (
                <View
                  key={i}
                  style={{
                    padding: pxToDp(10),
                    borderBottomColor: '#ccc',
                    borderBottomWidth: pxToDp(0.5),
                  }}>
                  {/* 2.2.1 用户信息 开始 */}
                  <View style={{flexDirection: 'row'}}>
                    <View style={{paddingRight: pxToDp(15)}}>
                      <Image
                        style={{
                          width: pxToDp(40),
                          height: pxToDp(40),
                          borderRadius: pxToDp(20),
                        }}
                        source={{uri: v.logo}}
                      />
                    </View>

                    <View style={{flex: 2, justifyContent: 'space-around'}}>
                      <View
                        style={{flexDirection: 'row', alignItems: 'center'}}>
                        <Text style={{color: '#555'}}>{v.nickName}</Text>
                        <IconFont
                          style={{
                            marginLeft: pxToDp(5),
                            marginRight: pxToDp(5),
                            fontSize: pxToDp(18),
                            color: v.sex === '女' ? '#b564bf' : 'red',
                          }}
                          name={
                            v.sex === '女' ? 'icontanhuanv' : 'icontanhuanan'
                          }
                        />
                        <Text style={{color: '#555'}}>{v.age}岁</Text>
                      </View>
                      <View style={{flexDirection: 'row'}}>
                        <Text>| </Text>
                        {v.tags.map((vv, ii) => (
                          <Text
                            style={{color: '#555', marginRight: pxToDp(5)}}
                            key={ii}>
                            {vv} |
                          </Text>
                        ))}
                      </View>
                    </View>
                  </View>
                  {/* 2.2.1 用户信息 结束 */}

                  {/* 2.3 创作内容 开始 */}
                  <View style={{marginTop: pxToDp(8)}}>
                    <Text style={{color: '#666'}}>{v.text}</Text>
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
                    {v.medias.map((vv, ii) => (
                      <TouchableOpacity
                        onPress={() => this.handleShowAlbum(i, ii)}
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
                </View>
              ))}
            </View>
            {/* 2.2 列表 结束 */}

            {this.params.page >= this.totalPages ? (
              <View
                style={{
                  height: pxToDp(80),
                  alignItems: 'center',
                  justifyContent: 'center',
                }}>
                <Text style={{color: '#666'}}>没有更多数据了</Text>
              </View>
            ) : (
              <></>
            )}
          </View>
          {/* 2.0 创作 结束 */}

          <Modal visible={showAlbum} transparent={true}>
            <ImageViewer
              onClick={() => this.setState({showAlbum: false})}
              imageUrls={imgUrls}
              index={currentIndex}
            />
          </Modal>
        </View>
      </>
    );
  }
}
export default Index;
