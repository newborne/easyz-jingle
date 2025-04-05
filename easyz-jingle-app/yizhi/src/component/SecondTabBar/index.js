import React, {Component} from 'react';
import {Text, View, TouchableOpacity} from 'react-native';
import {pxToDp} from '@src/util/pxToDp';
class Index extends Component {
  render() {
    // console.log(this.props);
    const {goToPage, tabs, activeTab} = this.props;
    // goToPage 函数 负责跳转页面
    // tabs 标题数组
    // activeTab 当前激活选中的索引
    return (
      <View
        style={{
          height: pxToDp(48),
          flexDirection: 'row',
          justifyContent: 'space-around',
        }}>
        {tabs.map((v, i) => (
          <TouchableOpacity
            key={i}
            onPress={() => goToPage(i)}
            style={{
              justifyContent: 'center',
              borderBottomColor: '#666',
              borderBottomWidth: activeTab === i ? pxToDp(2) : 0,
            }}>
            <Text
              style={{
                color: '#666',
                fontSize: pxToDp(18),
              }}>
              {v}
            </Text>
          </TouchableOpacity>
        ))}
      </View>
    );
  }
}

export default Index;
