import React, {Component} from 'react';
import {Text, ImageBackground, TouchableOpacity} from 'react-native';
import {pxToDp} from '@src/util/pxToDp';
class Index extends Component {
  render() {
    // console.log(this.props);
    const {goToPage, tabs, activeTab} = this.props;
    // goToPage 函数 负责跳转页面
    // tabs 标题数组
    // activeTab 当前激活选中的索引
    return (
      <ImageBackground
        style={{
          height: pxToDp(100),
          flexDirection: 'row',
          paddingTop: pxToDp(30),
          paddingLeft: pxToDp(24),
          paddingRight: pxToDp(24),
          justifyContent: 'space-evenly',
        }}
        source={require('@src/res/image/tabbar.png')}>
        {tabs.map((v, i) => (
          <TouchableOpacity
            key={i}
            onPress={() => goToPage(i)}
            style={{
              justifyContent: 'center',
              borderBottomColor: '#ffffff',
              borderBottomWidth: activeTab === i ? pxToDp(3) : 0,
            }}>
            <Text
              style={{
                color: '#ffffff',
                fontSize: activeTab === i ? pxToDp(26) : pxToDp(24),
              }}>
              {v}
            </Text>
          </TouchableOpacity>
        ))}
      </ImageBackground>
    );
  }
}

export default Index;
