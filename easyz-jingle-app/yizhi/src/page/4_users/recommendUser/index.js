import React, {Component} from 'react';
import {View, StatusBar, Text, Image, ScrollView} from 'react-native';
import {pxToDp} from '@src/util/pxToDp';
import RecommendUserHead from '@src/component/RecommendUserHead';
import TodayBest from '@src/component/TodayBest';
import IconFont from '@src/component/IconFont';
import TouchableScale from 'react-native-touchable-scale';
import {NavigationContext} from '@react-navigation/native';
import Request from '@src/util/Request';
import {USERS_RECOMMEND_USER_LIST, USERS_TODAYBEST} from '@src/util/Api';
class Index extends Component {
  static contextType = NavigationContext;
  state = {
    todayBest: {},
    // 接口要的数据
    params: {
      page: 1,
      pagesize: 10,
      sex: '',
      lastLogin: '',
      city: '',
      edu: '',
    },
    // 推荐朋友 数组
    recommendUserInfo: [],
  };
  componentDidMount() {
    this.getRecommendUserInfo();
  }

  获取推荐朋友;
  getRecommendUserInfo = async () => {
    const res1 = await Request.privateGet(USERS_RECOMMEND_USER_LIST, {
      ...this.state.params,
    });
    const res2 = await Request.privateGet(USERS_TODAYBEST);

    this.setState({recommendUserInfo: res1.data.records});
    this.setState({todayBest: res2.data});
  };
  render() {
    const {recommendUserInfo, todayBest} = this.state;
    return (
      <ScrollView>
        <View style={{backgroundColor: '#eee'}}>
          <View
            style={{
              height: pxToDp(128),
              justifyContent: 'center',
              alignItems: 'center',
              backgroundColor: '#fff',
            }}>
            <RecommendUserHead />
          </View>
          {/* 2.2 标题 开始 */}
          <View
            style={{
              height: pxToDp(48),
              flexDirection: 'row',
              justifyContent: 'center',
              alignItems: 'center',
              backgroundColor: '#eee',
              marginTop: pxToDp(4),
              marginBottom: pxToDp(4),
            }}>
            <Text style={{color: '#333', fontSize: pxToDp(24)}}>今日最佳</Text>
          </View>
          {/* 2.0 今日最佳推荐 开始 */}
          <TouchableScale
            friction={90} //
            tension={124} // These props are passed to the parent component (here TouchableScale)
            activeScale={0.95} //
            style={{
              marginLeft: pxToDp(12),
              marginRight: pxToDp(12),
            }}
            onPress={() =>
              this.context.navigate('UserDetail', {id: todayBest.friendId})
            }>
            <TodayBest />
          </TouchableScale>
          {/* 2.0 今日最佳推荐 结束 */}
          {/* 2.1 推荐 开始 */}
          <View>
            {/* 2.2 标题 开始 */}
            <View
              style={{
                height: pxToDp(48),
                flexDirection: 'row',
                justifyContent: 'center',
                alignItems: 'center',
                backgroundColor: '#eee',
                marginTop: pxToDp(4),
                marginBottom: pxToDp(4),
              }}>
              <Text style={{color: '#333', fontSize: pxToDp(24)}}>推荐</Text>
              <TouchableScale
                friction={90} //
                tension={124} // These props are passed to the parent component (here TouchableScale)
                activeScale={0.95} //
                style={{
                  position: 'absolute',
                  right: pxToDp(10),
                }}>
                <IconFont
                  style={{
                    color: '#333',
                    fontSize: pxToDp(24),
                  }}
                  name="iconFilter"
                />
              </TouchableScale>
            </View>
            {/* 2.2 标题 结束 */}
            {/* 2.3 列表内容 开始 */}
            <View
              style={{
                paddingLeft: pxToDp(4),
                paddingRight: pxToDp(4),
                marginBottom: pxToDp(12),
              }}>
              {recommendUserInfo.map((v, i) => (
                <TouchableScale
                  friction={90} //
                  tension={124} // These props are passed to the parent component (here TouchableScale)
                  activeScale={0.95} //
                  key={i}
                  onPress={() =>
                    this.context.navigate('UserDetail', {id: v.friendId})
                  }
                  style={{
                    flexDirection: 'row',
                    paddingTop: pxToDp(15),
                    paddingBottom: pxToDp(15),
                    backgroundColor: '#ffffff',
                    borderRadius: pxToDp(18),
                    marginBottom: pxToDp(4),
                    marginLeft: pxToDp(4),
                    marginRight: pxToDp(4),
                  }}>
                  {/* 图片 */}
                  <View
                    style={{
                      paddingLeft: pxToDp(15),
                      paddingRight: pxToDp(15),
                    }}>
                    <Image
                      style={{
                        width: pxToDp(56),
                        height: pxToDp(56),
                        borderRadius: pxToDp(28),
                      }}
                      source={{uri: v.logo}}
                    />
                  </View>
                  {/* 名称 */}
                  <View style={{flex: 2, justifyContent: 'space-around'}}>
                    <View style={{flexDirection: 'row', alignItems: 'center'}}>
                      <Text style={{color: '#666'}}>{v.nickName} </Text>
                      <IconFont
                        style={{
                          marginLeft: pxToDp(5),
                          marginRight: pxToDp(5),
                          fontSize: pxToDp(18),
                          color: v.sex === 'woman' ? '#36cfc9' : '#37DC8A',
                        }}
                        name={
                          v.sex === 'woman' ? 'iconWomanMini' : 'iconManMini'
                        }
                      />
                      <Text style={{color: '#666'}}>{v.age}岁</Text>
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
                  {/* 相似度 */}
                  <View
                    style={{
                      flexDirection: 'row',
                      alignItems: 'center',
                      width: pxToDp(100),
                      justifyContent: 'center',
                    }}>
                    <IconFont
                      name="iconSimilarity"
                      style={{color: '#37DC8A', fontSize: pxToDp(48)}}
                    />
                    <Text
                      style={{
                        position: 'absolute',
                        color: '#fff',
                        fontSize: pxToDp(18),
                      }}>
                      {v.similarity}
                    </Text>
                  </View>
                </TouchableScale>
              ))}
            </View>
            {/* 2.3 列表内容 结束 */}
          </View>
          {/* 2.1 推荐 结束 */}
        </View>
      </ScrollView>
    );
  }
}

export default Index;
