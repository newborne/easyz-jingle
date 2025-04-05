import React, {Component} from 'react';
import {View, Text} from 'react-native';
import {pxToDp} from '@src/util/pxToDp';
import {NavigationContext} from '@react-navigation/native';
import IconFont from '../IconFont';
import TouchableScale from 'react-native-touchable-scale';
class Index extends Component {
  static contextType = NavigationContext;
  goPage = page => {
    this.context.navigate(page);
  };
  render() {
    return (
      <View
        style={{
          flexDirection: 'row',
          width: '100%',
          justifyContent: 'space-around',
        }}>
        <TouchableScale
          style={{alignItems: 'center'}}
          onPress={() => this.goPage('TestValue')}>
          <View
            style={{
              width: pxToDp(64),
              height: pxToDp(64),
              borderRadius: pxToDp(32),
              backgroundColor: '#15831C',
              justifyContent: 'center',
              alignItems: 'center',
            }}>
            <IconFont
              name="iconTestValue"
              style={{fontSize: pxToDp(46), color: '#fff'}}
            />
          </View>
          <Text
            style={{fontSize: pxToDp(16), marginTop: pxToDp(4), color: '#666'}}>
            测文值
          </Text>
        </TouchableScale>
        <TouchableScale
          style={{alignItems: 'center'}}
          onPress={() => this.goPage('Near')}>
          <View
            style={{
              width: pxToDp(64),
              height: pxToDp(64),
              borderRadius: pxToDp(32),
              backgroundColor: '#FF6F00',
              justifyContent: 'center',
              alignItems: 'center',
            }}>
            <IconFont
              name="iconNear"
              style={{fontSize: pxToDp(38), color: '#fff'}}
            />
          </View>
          <Text
            style={{fontSize: pxToDp(16), marginTop: pxToDp(4), color: '#666'}}>
            附近的人
          </Text>
        </TouchableScale>
        <TouchableScale
          style={{alignItems: 'center'}}
          onPress={() => this.goPage('Sway')}>
          <View
            style={{
              width: pxToDp(64),
              height: pxToDp(64),
              borderRadius: pxToDp(32),
              backgroundColor: '#E32C2D',
              justifyContent: 'center',
              alignItems: 'center',
            }}>
            <IconFont
              name="iconSway"
              style={{fontSize: pxToDp(38), color: '#fff'}}
            />
          </View>
          <Text
            style={{fontSize: pxToDp(16), marginTop: pxToDp(4), color: '#666'}}>
            摇一摇
          </Text>
        </TouchableScale>
      </View>
    );
  }
}

export default Index;
