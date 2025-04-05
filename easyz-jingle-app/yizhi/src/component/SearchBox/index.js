import React, {Component} from 'react';
import {View, TextInput} from 'react-native';
import IconFont from '@src/component/IconFont';
import {pxToDp} from '@src/util/pxToDp';
import TouchableScale from 'react-native-touchable-scale';
class Index extends Component {
  render() {
    return (
      <View
        style={{
          flexDirection: 'row',
          height: pxToDp(50),
          borderRadius: pxToDp(32),
          marginTop: pxToDp(10),
          marginLeft: pxToDp(20),
          marginRight: pxToDp(20),
          backgroundColor: '#e7e7e7',
          ...this.props.style,
        }}>
        <View style={{flex: 1}} />
        <View
          style={{
            flex: 1,
            alignItems: 'center',
            justifyContent: 'center',
          }}>
          <TextInput
            value={this.props.value}
            onChangeText={this.props.onChangeText}
            placeholder="搜索"
            style={{
              fontSize: pxToDp(20),
            }}
          />
        </View>
        <View
          style={{
            flex: 1,
            alignItems: 'flex-end',
            justifyContent: 'center',
          }}>
          <TouchableScale>
            <IconFont
              style={{
                marginRight: pxToDp(15),
                color: '#999',
                fontSize: pxToDp(30),
              }}
              name="iconSort"
            />
          </TouchableScale>
        </View>
      </View>
    );
  }
}
export default Index;
