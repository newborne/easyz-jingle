import React, {Component} from 'react';
import {
  View,
  Text,
  StatusBar,
  TouchableOpacity,
  Image,
  ScrollView,
  ImageBackground,
  RefreshControl,
} from 'react-native';
import {NavigationContext} from '@react-navigation/native';
import {ListItem, Avatar} from 'react-native-elements';
import LinearGradient from 'react-native-linear-gradient';
import TouchableScale from 'react-native-touchable-scale';
import IconFont from '@src/component/IconFont';
import {pxToDp} from '@src/util/pxToDp';
import {BoxShadow} from 'react-native-shadow';
import Geo from '@src/util/Geo';
import {BASE_URI, USERS_FOLLOW_COUNTS} from '@src/util/Api';
import Request from '@src/util/Request';
import {ListItemBase} from 'react-native-elements/dist/list/ListItemBase';
class Index extends Component {
  static contextType = NavigationContext;
  state = {
    user: {
      age: 12,
      sex: 'man',
      logo: 'https://z3.ax1x.com/2021/05/22/gqLnWq.png',
      userName: '奋斗的少年',
    },
    city: '',
    // 粉丝的数量
    fanCount: 0,
    // 喜欢的数量
    followCount: 0,
    // 相互关注的数量
    mutualFollowCount: 0,
    // 控制 加载中的组件的切换显示
    refreshing: false,
  };
  componentDidMount() {
    this.getCityByLocation();
    this.getList();
  }
  getCityByLocation = async () => {
    const res = await Geo.getCityByLocation();
    this.setState({city: res.regeocode.addressComponent.city});
  };
  getList = async () => {
    const res = await Request.privateGet(USERS_FOLLOW_COUNTS);
    console.log(res);
    const mutualFollowCount = res.data.mutualFollowCount;
    const followCount = res.data.followCount;
    const fanCount = res.data.fanCount;
    this.setState({mutualFollowCount, followCount, fanCount});

    return Promise.resolve();
  };
  // 下拉刷新事件
  onRefresh = async () => {
    this.setState({refreshing: true});
    await this.getList();
    this.setState({refreshing: false});
  };
  render() {
    const {user, city, fanCount, followCount, mutualFollowCount, refreshing} =
      this.state;
    return (
      <View style={{flex: 1, backgroundColor: '#f7f7f7'}}>
        <ScrollView
          refreshControl={
            <RefreshControl
              refreshing={refreshing}
              onRefresh={this.onRefresh}
            />
          }>
          <ImageBackground
            source={require('@src/res/image/material_head.png')}
            style={{height: pxToDp(340), position: 'relative'}}>
            <TouchableOpacity
              onPress={() => this.context.navigate('UpdateUserInfo')}
              style={{
                position: 'absolute',
                top: pxToDp(60),
                right: pxToDp(30),
              }}>
              <IconFont
                name="iconEdit"
                style={{
                  color: '#fff',
                  fontSize: pxToDp(24),
                }}
              />
            </TouchableOpacity>

            <TouchableOpacity
              style={{
                zIndex: 1,
                flexDirection: 'column',
                paddingTop: pxToDp(15),
                paddingBottom: pxToDp(15),
                marginTop: pxToDp(20),
                alignSelf: 'center',
                alignItems: 'center',
                width: '50%',
                height: pxToDp(180),
              }}>
              {/* 图片 */}
              <View style={{padding: pxToDp(15)}}>
                <Image
                  style={{
                    width: pxToDp(64),
                    height: pxToDp(64),
                    borderRadius: pxToDp(32),
                  }}
                  source={{uri: user.logo}}
                />
              </View>
              {/* 名称 */}
              <View style={{justifyContent: 'space-around'}}>
                <View
                  style={{
                    flexDirection: 'row',
                    alignItems: 'center',
                    alignSelf: 'center',
                  }}>
                  <Text
                    style={{
                      color: '#fff',
                      fontWeight: 'bold',
                      fontSize: pxToDp(17),
                    }}>
                    {user.userName}
                  </Text>
                </View>
                <View style={{flexDirection: 'row', marginTop: pxToDp(2)}}>
                  <View
                    style={{
                      flexDirection: 'row',
                      backgroundColor: '#fff',
                      borderRadius: pxToDp(12),
                      paddingLeft: pxToDp(3),
                      paddingRight: pxToDp(3),
                      alignItems: 'center',
                      alignSelf: 'center',
                      margin: pxToDp(5),
                    }}>
                    <IconFont
                      style={{
                        fontSize: pxToDp(18),
                        marginLeft: pxToDp(5),
                        marginRight: pxToDp(5),
                        color: user.sex === 'woman' ? '#36cfc9' : '#37DC8A',
                      }}
                      name={user.sex === 'woman' ? 'iconWoman' : 'iconManMini'}
                    />
                    <Text style={{color: '#555'}}>{user.age}岁</Text>
                  </View>
                  <View
                    style={{
                      flexDirection: 'row',
                      backgroundColor: '#fff',
                      borderRadius: pxToDp(12),
                      paddingLeft: pxToDp(3),
                      paddingRight: pxToDp(3),
                      margin: pxToDp(5),
                      alignItems: 'center',
                      alignSelf: 'center',
                    }}>
                    <IconFont style={{color: '#555'}} name="iconMap" />
                    <Text style={{color: '#555', marginLeft: pxToDp(5)}}>
                      {city}
                    </Text>
                  </View>
                </View>
              </View>
            </TouchableOpacity>
          </ImageBackground>
          <View
            style={{
              position: 'absolute',
              marginTop: pxToDp(200),
              flexDirection: 'row',
              alignItems: 'center',
              padding: pxToDp(15),
              justifyContent: 'space-around',
              width: '100%',
            }}>
            <BoxShadow
              setting={{
                width: pxToDp(88),
                height: pxToDp(68),
                color: '#000',
                border: pxToDp(1),
                radius: pxToDp(12),
                opacity: 0.1,
                x: 2,
                y: 2,
              }}>
              <TouchableScale
                onPress={() => this.context.navigate('Friend', 0)}
                style={{
                  height: pxToDp(68),
                  width: pxToDp(88),
                  borderRadius: pxToDp(12),
                }}>
                <LinearGradient
                  style={{
                    height: pxToDp(68),
                    width: pxToDp(88),
                    borderRadius: pxToDp(12),
                    justifyContent: 'center',
                    padding: pxToDp(10),
                  }}
                  colors={['#fff', '#ff5314']}
                  start={{x: 0, y: 0}}
                  end={{x: 1, y: -0.8}}>
                  <Text style={{color: '#666', fontSize: pxToDp(22)}}>
                    {mutualFollowCount}
                  </Text>
                  <Text style={{color: '#666', fontSize: pxToDp(16)}}>
                    互相关注
                  </Text>
                  <View
                    style={{
                      position: 'absolute',
                      top: pxToDp(4),
                      right: pxToDp(8),
                    }}>
                    <IconFont
                      name="iconMutualFollow"
                      style={{fontSize: pxToDp(36), color: '#fff'}}
                    />
                  </View>
                </LinearGradient>
              </TouchableScale>
            </BoxShadow>
            <BoxShadow
              setting={{
                width: pxToDp(88),
                height: pxToDp(68),
                color: '#000',
                border: pxToDp(1),
                radius: pxToDp(12),
                opacity: 0.1,
                x: 2,
                y: 2,
              }}>
              <TouchableScale
                onPress={() => this.context.navigate('Friend', 1)}
                style={{
                  height: pxToDp(68),
                  width: pxToDp(88),
                  borderRadius: pxToDp(12),
                }}>
                <LinearGradient
                  style={{
                    height: pxToDp(68),
                    width: pxToDp(88),
                    borderRadius: pxToDp(12),
                    justifyContent: 'center',
                    padding: pxToDp(10),
                  }}
                  colors={['#fff', '#2fb4f9']}
                  start={{x: 0, y: 0}}
                  end={{x: 1, y: -0.8}}>
                  <Text style={{color: '#666', fontSize: pxToDp(22)}}>
                    {followCount}
                  </Text>
                  <Text style={{color: '#666', fontSize: pxToDp(16)}}>
                    关注
                  </Text>
                  <View
                    style={{
                      position: 'absolute',
                      top: pxToDp(4),
                      right: pxToDp(8),
                    }}>
                    <IconFont
                      name="iconFollow"
                      style={{fontSize: pxToDp(36), color: '#fff'}}
                    />
                  </View>
                </LinearGradient>
              </TouchableScale>
            </BoxShadow>
            <BoxShadow
              setting={{
                width: pxToDp(88),
                height: pxToDp(68),
                color: '#000',
                border: pxToDp(1),
                radius: pxToDp(12),
                opacity: 0.1,
                x: 2,
                y: 2,
              }}>
              <TouchableScale
                onPress={() => this.context.navigate('Friend', 2)}
                style={{
                  height: pxToDp(68),
                  width: pxToDp(88),
                  borderRadius: pxToDp(12),
                }}>
                <LinearGradient
                  style={{
                    height: pxToDp(68),
                    width: pxToDp(88),
                    borderRadius: pxToDp(12),
                    justifyContent: 'center',
                    padding: pxToDp(10),
                  }}
                  colors={['#fff', '#1adbde']}
                  start={{x: 0, y: 0}}
                  end={{x: 1, y: -0.8}}>
                  <Text style={{color: '#666', fontSize: pxToDp(22)}}>
                    {fanCount}
                  </Text>
                  <Text style={{color: '#666', fontSize: pxToDp(16)}}>
                    粉丝
                  </Text>
                  <View
                    style={{
                      position: 'absolute',
                      top: pxToDp(4),
                      right: pxToDp(8),
                    }}>
                    <IconFont
                      name="iconFan"
                      style={{fontSize: pxToDp(36), color: '#fff'}}
                    />
                  </View>
                </LinearGradient>
              </TouchableScale>
            </BoxShadow>
            <BoxShadow
              setting={{
                width: pxToDp(88),
                height: pxToDp(68),
                color: '#000',
                border: pxToDp(1),
                radius: pxToDp(12),
                opacity: 0.1,
                x: 2,
                y: 2,
              }}>
              <TouchableScale
                onPress={() => this.context.navigate('Friend', 3)}
                style={{
                  height: pxToDp(68),
                  width: pxToDp(88),
                  borderRadius: pxToDp(12),
                }}>
                <LinearGradient
                  style={{
                    height: pxToDp(68),
                    width: pxToDp(88),
                    borderRadius: pxToDp(12),
                    justifyContent: 'center',
                    padding: pxToDp(10),
                  }}
                  colors={['#fff', '#ebc969']}
                  start={{x: 0, y: 0}}
                  end={{x: 1, y: -0.8}}>
                  <Text style={{color: '#666', fontSize: pxToDp(22)}}>
                    {mutualFollowCount}
                  </Text>
                  <Text style={{color: '#666', fontSize: pxToDp(16)}}>
                    访客
                  </Text>
                  <View
                    style={{
                      position: 'absolute',
                      top: pxToDp(4),
                      right: pxToDp(8),
                    }}>
                    <IconFont
                      name="iconFan"
                      style={{fontSize: pxToDp(36), color: '#fff'}}
                    />
                  </View>
                </LinearGradient>
              </TouchableScale>
            </BoxShadow>
          </View>
          <View
            style={{
              paddingTop: pxToDp(38),
              paddingBottom: pxToDp(12),
            }}>
            <ListItem
              style={{
                marginLeft: pxToDp(40),
              }}
              containerStyle={{
                borderRadius: pxToDp(12),
              }}
              Component={TouchableScale}
              onPress={() => this.context.navigate('MyPost')}
              friction={90} //
              tension={124} // These props are passed to the parent component (here TouchableScale)
              activeScale={0.95} //
              linearGradientProps={{
                colors: ['#39DBD5', '#f7f7f7'],
                start: {x: 0, y: 0},
                end: {x: 1, y: 1},
              }}
              ViewComponent={LinearGradient} // Only if no expo
            >
              <Avatar rounded>
                <IconFont
                  name="iconPost"
                  style={{fontSize: pxToDp(36), color: '#fff'}}
                />
              </Avatar>
              <ListItem.Content>
                <ListItem.Title style={{color: 'white', fontWeight: 'bold'}}>
                  我的文章
                </ListItem.Title>
                <ListItem.Subtitle style={{color: 'white'}}>
                  My Post
                </ListItem.Subtitle>
              </ListItem.Content>
              <ListItem.Chevron color="#39dbd5" />
            </ListItem>
            <ListItem
              onPress={() => this.context.navigate('MyVideo')}
              style={{
                marginTop: pxToDp(12),
                marginLeft: pxToDp(100),
              }}
              containerStyle={{
                borderRadius: pxToDp(12),
              }}
              Component={TouchableScale}
              friction={90} //
              tension={124} // These props are passed to the parent component (here TouchableScale)
              activeScale={0.95} //
              linearGradientProps={{
                colors: ['#39DBD5', '#f7f7f7'],
                start: {x: 0, y: 0},
                end: {x: 1, y: 1},
              }}
              ViewComponent={LinearGradient} // Only if no expo
            >
              <Avatar rounded>
                <IconFont
                  name="iconVideo"
                  style={{fontSize: pxToDp(36), color: '#fff'}}
                />
              </Avatar>
              <ListItem.Content>
                <ListItem.Title style={{color: 'white', fontWeight: 'bold'}}>
                  我的短视频
                </ListItem.Title>
                <ListItem.Subtitle style={{color: 'white'}}>
                  My Video
                </ListItem.Subtitle>
              </ListItem.Content>
              <ListItem.Chevron color="#39dbd5" />
            </ListItem>
            <ListItem
              onPress={() => this.context.navigate('Feedback')}
              style={{
                marginTop: pxToDp(12),
                marginLeft: pxToDp(170),
              }}
              containerStyle={{
                borderRadius: pxToDp(12),
              }}
              Component={TouchableScale}
              friction={90} //
              tension={124} // These props are passed to the parent component (here TouchableScale)
              activeScale={0.95} //
              linearGradientProps={{
                colors: ['#39DBD5', '#f7f7f7'],
                start: {x: 0, y: 0},
                end: {x: 1, y: 1},
              }}
              ViewComponent={LinearGradient} // Only if no expo
            >
              <Avatar rounded>
                <IconFont
                  name="iconEdit"
                  style={{fontSize: pxToDp(36), color: '#fff'}}
                />
              </Avatar>
              <ListItem.Content>
                <ListItem.Title style={{color: 'white', fontWeight: 'bold'}}>
                  反馈
                </ListItem.Title>
                <ListItem.Subtitle style={{color: 'white'}}>
                  Feedback
                </ListItem.Subtitle>
              </ListItem.Content>
              <ListItem.Chevron color="#39dbd5" />
            </ListItem>
            <ListItem
              onPress={() => this.context.navigate('Shop')}
              style={{
                marginTop: pxToDp(12),
                marginLeft: pxToDp(210),
              }}
              containerStyle={{
                borderRadius: pxToDp(12),
              }}
              Component={TouchableScale}
              friction={90} //
              tension={124} // These props are passed to the parent component (here TouchableScale)
              activeScale={0.95} //
              linearGradientProps={{
                colors: ['#39DBD5', '#f7f7f7'],
                start: {x: 0, y: 0},
                end: {x: 1, y: 1},
              }}
              ViewComponent={LinearGradient} // Only if no expo
            >
              <Avatar rounded>
                <IconFont
                  name="iconShop"
                  style={{fontSize: pxToDp(36), color: '#fff'}}
                />
              </Avatar>
              <ListItem.Content>
                <ListItem.Title style={{color: 'white', fontWeight: 'bold'}}>
                  周边好物
                </ListItem.Title>
                <ListItem.Subtitle style={{color: 'white'}}>
                  Nice Thing
                </ListItem.Subtitle>
              </ListItem.Content>
              <ListItem.Chevron color="#39dbd5" />
            </ListItem>
            <ListItem
              onPress={() => this.context.navigate('Setting')}
              style={{
                marginTop: pxToDp(12),
                marginLeft: pxToDp(40),

                marginBottom: pxToDp(12),
              }}
              containerStyle={{
                borderRadius: pxToDp(12),
              }}
              Component={TouchableScale}
              friction={90} //
              tension={124} // These props are passed to the parent component (here TouchableScale)
              activeScale={0.95} //
              linearGradientProps={{
                colors: ['#39DBD5', '#f7f7f7'],
                start: {x: 0, y: 0},
                end: {x: 1, y: 1},
              }}
              ViewComponent={LinearGradient} // Only if no expo
            >
              <Avatar rounded>
                <IconFont
                  name="iconSetting"
                  style={{fontSize: pxToDp(36), color: '#fff'}}
                />
              </Avatar>
              <ListItem.Content>
                <ListItem.Title style={{color: 'white', fontWeight: 'bold'}}>
                  设置
                </ListItem.Title>
                <ListItem.Subtitle style={{color: 'white'}}>
                  Setting
                </ListItem.Subtitle>
              </ListItem.Content>
              <ListItem.Chevron color="#39dbd5" />
            </ListItem>
            <TouchableScale
              friction={90} //
              tension={124} // These props are passed to the parent component (here TouchableScale)
              activeScale={0.95} //
            >
              <IconFont
                name="iconLogo"
                style={{
                  position: 'absolute',
                  left: pxToDp(20),
                  bottom: pxToDp(120),
                  fontSize: pxToDp(128),
                  color: '#36cfc9',
                }}
              />
            </TouchableScale>
          </View>
        </ScrollView>
      </View>
    );
  }
}

export default Index;
