import React, {Component} from 'react';
import {
  View,
  Text,
  Image,
  StatusBar,
  ImageBackground,
  TouchableOpacity,
} from 'react-native';
import {USERS_NEAR} from '@src/util/Api';
import {pxToDp, screenWidth, screenHeight} from '@src/util/pxToDp';
import IconFont from '@src/component/IconFont';
import {Overlay} from 'teaset';
import FilterPanel from '@src/component/FilterPanel';
import Request from '@src/util/Request';
import {NavigationContext} from '@react-navigation/native';
class Index extends Component {
  static contextType = NavigationContext;
  params = {
    sex: 'man',
    distance: 8000,
  };
  state = {
    distance: 8000,
    list: [
      // {
      //   uid: 1,
      //   logo: 'https://z3.ax1x.com/2021/05/22/gqLnWq.png',
      //   userName: '用户1',
      //   distance: 1000,
      // },
      // {
      //   uid: 2,
      //   logo: 'https://z3.ax1x.com/2021/05/22/gqLnWq.png',
      //   userName: '用户2',
      //   distance: 2000,
      // },
      // {
      //   uid: 3,
      //   logo: 'https://z3.ax1x.com/2021/05/22/gqLnWq.png',
      //   userName: '用户3',
      //   distance: 3000,
      // },
      // {
      //   uid: 4,
      //   logo: 'https://z3.ax1x.com/2021/05/22/gqLnWq.png',
      //   userName: '用户4',
      //   distance: 4000,
      // },
      // {
      //   uid: 5,
      //   logo: 'https://z3.ax1x.com/2021/05/22/gqLnWq.png',
      //   userName: '用户5',
      //   distance: 6000,
      // },
      // {
      //   uid: 6,
      //   logo: 'https://z3.ax1x.com/2021/05/22/gqLnWq.png',
      //   userName: '用户6',
      //   distance: 20000,
      // },
      // {
      //   uid: 7,
      //   logo: 'https://z3.ax1x.com/2021/05/22/gqLnWq.png',
      //   userName: '用户7',
      //   distance: 200000,
      // },
    ],
  };
  componentDidMount() {
    this.getList();
  }

  WHMap = {
    wh1: {width: pxToDp(50), height: pxToDp(50)},
    wh2: {width: pxToDp(45), height: pxToDp(45)},
    wh3: {width: pxToDp(40), height: pxToDp(40)},
    wh4: {width: pxToDp(35), height: pxToDp(35)},
    wh5: {width: pxToDp(30), height: pxToDp(30)},
    wh6: {width: pxToDp(25), height: pxToDp(25)},
  };

  // 根据 dist来返回对应的宽度的档次
  getWidthHeight = distance => {
    if (distance < 1000) {
      return 'wh1';
    }
    if (distance < 5000) {
      return 'wh2';
    }
    if (distance < 10000) {
      return 'wh3';
    }
    if (distance < 15000) {
      return 'wh4';
    }
    if (distance < 20000) {
      return 'wh5';
    }
    return 'wh6';
  };

  // 获取附近的数据
  getList = async () => {
    const res = await Request.privateGet(USERS_NEAR, this.params);
    console.log(res);
    this.setState({list: res.data});
  };

  // 点击筛选按钮
  handleFilterShow = () => {
    let overlayViewRef = null;
    let overlayView = (
      <Overlay.View
        modal={true}
        overlayOpacity={0.3}
        ref={v => (overlayViewRef = v)}>
        {/* 显示 筛选组件 */}
        <FilterPanel
          onSubmitFilter={this.handleSubmitFilter}
          params={this.params}
          onClose={() => overlayViewRef.close()}
        />
      </Overlay.View>
    );
    Overlay.show(overlayView);
  };
  // 获取了筛选结果
  handleSubmitFilter = filterParams => {
    this.params = filterParams;
    this.setState({distance: filterParams.distance});
    this.getList();
  };
  render() {
    const {list, distance} = this.state;
    return (
      <View
        style={{
          flex: 1,
          position: 'relative',
          justifyContent: 'center',
          backgroundColor: '#000032',
        }}>
        <StatusBar backgroundColor={'transparent'} translucent={true} />

        <View
          style={{
            alignSelf: 'center',
            width: pxToDp(400),
            height: pxToDp(400),
            borderRadius: pxToDp(200),
            overflow: 'hidden',
          }}>
          <Image
            style={{
              width: pxToDp(400),
              height: pxToDp(400),
            }}
            source={require('@src/res/image/nearby.webp')}
          />
        </View>
        <TouchableOpacity
          style={{
            backgroundColor: '#fff',
            position: 'absolute',
            opacity: 0.85,
            right: '5%',
            bottom: '6%',
            width: pxToDp(80),
            height: pxToDp(36),
            borderRadius: pxToDp(18),
            alignItems: 'center',
            justifyContent: 'space-around',
            flexDirection: 'row',
            zIndex: 1000,
          }}
          onPress={this.handleFilterShow}>
          <IconFont
            style={{color: '#36cf39', fontSize: pxToDp(20)}}
            name="iconFilter"
          />
          <Text style={{fontSize: pxToDp(14), color: '#666'}}>筛选</Text>
        </TouchableOpacity>
        {list.map((v, i) => {
          const whMap = this.WHMap[this.getWidthHeight(distance)];
          const tx =
            Math.random() * (screenWidth / 2 - whMap.width) + pxToDp(100);
          const ty =
            Math.random() * (screenHeight / 2 - whMap.height) + pxToDp(200);
          return (
            <TouchableOpacity
              onPress={() =>
                this.context.navigate('UserDetail', {id: v.userId})
              }
              key={i}
              style={{position: 'absolute', left: tx, top: ty}}>
              <ImageBackground
                source={require('@src/res/image/circle.png')}
                style={{
                  ...whMap,
                  position: 'absolute',
                  alignItems: 'center',
                  width: whMap.width + pxToDp(12),
                  height: whMap.height + pxToDp(12),
                  justifyContent: 'center',
                }}>
                <Text
                  numberOfLines={1}
                  style={{
                    width: pxToDp(64),
                    color: '#fff',
                    position: 'absolute',
                    fontSize: pxToDp(12),
                    top: -pxToDp(15),
                  }}>
                  {v.nickName}
                </Text>
                <Image
                  style={{
                    width: whMap.width,
                    height: whMap.width,
                    borderRadius: whMap.width / 2,
                  }}
                  source={{uri: v.logo}}
                />
              </ImageBackground>
            </TouchableOpacity>
          );
        })}
        <View
          style={{
            position: 'absolute',
            top: pxToDp(100),
            width: '100%',
            alignItems: 'center',
          }}>
          <Text style={{color: '#fff', fontSize: pxToDp(24)}}>
            附近
            <Text style={{color: '#36cfc9'}}>{distance}</Text>
            米有
            <Text style={{color: '#36cfc9'}}>{list.length}</Text>
            个用户
          </Text>
        </View>
      </View>
    );
  }
}
export default Index;
