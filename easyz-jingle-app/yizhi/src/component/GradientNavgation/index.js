import React, {Component} from 'react';
import {
  View,
  Text,
  ImageBackground,
  StatusBar,
  TouchableOpacity,
} from 'react-native';

import {NavigationContext} from '@react-navigation/native';
import {pxToDp} from '@src/util/pxToDp';
import IconFont from '@src/component/IconFont';
class Index extends Component {
  static contextType = NavigationContext;
  render() {
    return (
      <View>
        <StatusBar backgroundColor="transparent" translucent={true} />
        <ImageBackground
          source={require('@src/res/image/tabbar.png')}
          style={{
            paddingTop: pxToDp(24),
            height: pxToDp(100),
            flexDirection: 'row',
            alignItems: 'center',
            justifyContent: 'space-around',
          }}>
          <TouchableOpacity
            onPress={this.context.goBack}
            style={{
              width: pxToDp(80),
              position: 'absolute',
              left: pxToDp(18),
              bottom: pxToDp(20),
            }}>
            <IconFont
              style={{color: '#fff', fontSize: pxToDp(24)}}
              name="iconBack"
            />
          </TouchableOpacity>

          <Text style={{color: '#fff', fontSize: pxToDp(24)}}>
            {this.props.title}
          </Text>
        </ImageBackground>
      </View>
    );
  }
}
export default Index;
