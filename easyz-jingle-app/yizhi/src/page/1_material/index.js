import React, {Component} from 'react';
import {Image, View, Text, StatusBar} from 'react-native';
import TouchableScale from 'react-native-touchable-scale';
import {ImageHeaderScrollView} from 'react-native-image-header-scroll-view';

import MaterialHead from '@src/component/MaterialHead';
import {pxToDp} from '@src/util/pxToDp';
import {USERS_LIST, MATERIAL_RECOMMEND_V1} from '@src/util/Api';
import {NavigationContext} from '@react-navigation/native';
import Request from '@src/util/Request';

class Index extends Component {
  static contextType = NavigationContext;
  state = {
    list: [
      // "id": "622cafc43bdf8863a3f0b560",
      // 	"materialRid": "1",
      // 	"tags": [
      // 		"爱国"
      // 	],
      // 	"text": "捐躯赴国难，视死忽如归。 曹植 《白马篇》",
      // 	"loveCount": 0,
      // 	"hasLoved": 0
    ],
  };
  componentDidMount() {
    this.getlist();
  }
  getlist = async () => {
    const res1 = await Request.privateGet(MATERIAL_RECOMMEND_V1);
    if (res1.ok) {
      const list = res1.data.records;
      this.setState({list});
    }
    console.log(res1);
  };
  render() {
    const {list} = this.state;
    return (
      <ImageHeaderScrollView
        maxHeight={pxToDp(200)}
        minHeight={pxToDp(80)}
        headerImage={require('@src/res/image/material_head.png')}
        renderForeground={() => (
          <View
            style={{
              height: pxToDp(130),
              justifyContent: 'center',
              alignItems: 'center',
            }}>
            <StatusBar backgroundColor={'transparent'} translucent={true} />
            <MaterialHead />
          </View>
        )}>
        <View style={{backgroundColor: '#eee'}}>
          <View
            style={{
              height: pxToDp(48),
              flexDirection: 'row',
              justifyContent: 'center',
              backgroundColor: '#ffffff',
              alignItems: 'center',
            }}>
            <Text style={{color: '#333', fontSize: pxToDp(24)}}>推荐</Text>
          </View>
          <View
            style={{
              marginBottom: pxToDp(12),
              paddingLeft: pxToDp(4),
              paddingRight: pxToDp(4),
              paddingTop: pxToDp(8),
            }}>
            {list.map((v, i) => (
              <TouchableScale
                friction={90} //
                tension={124} // These props are passed to the parent component (here TouchableScale)
                activeScale={0.95} //
                onPress={() =>
                  this.context.navigate('MaterialDetail', {
                    materialRid: v.materialRid,
                  })
                }
                key={i}
                style={{
                  flexDirection: 'row',
                  padding: pxToDp(12),
                  backgroundColor: '#ffffff',
                  borderRadius: pxToDp(18),
                  marginBottom: pxToDp(4),
                  marginLeft: pxToDp(4),
                  marginRight: pxToDp(4),
                }}>
                <View
                  style={{
                    width: '90%',
                    marginLeft: pxToDp(10),
                    padding: pxToDp(15),
                  }}>
                  <Text numberOfLines={7} style={{color: '#666'}}>
                    {v.text}
                  </Text>
                </View>
                <View
                  style={{
                    width: pxToDp(24),
                    backgroundColor: '#36cfc9',
                    justifyContent: 'center',
                    alignSelf: 'center',
                    borderRadius: pxToDp(12),
                  }}>
                  <Text
                    style={{
                      marginTop: pxToDp(12),
                      marginBottom: pxToDp(12),
                      color: '#fff',
                      fontSize: pxToDp(12),
                      textAlign: 'center',
                    }}>
                    {v.tags}
                  </Text>
                </View>
                <Image
                  source={{uri: 'https://z3.ax1x.com/2021/06/09/2y4dKO.png'}}
                  style={{
                    width: pxToDp(36),
                    height: pxToDp(36),
                    position: 'absolute',
                    left: pxToDp(2),
                    top: pxToDp(2),
                  }}
                />
              </TouchableScale>
            ))}
          </View>
        </View>
      </ImageHeaderScrollView>
    );
  }
}

export default Index;
