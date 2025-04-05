import React, {Component} from 'react';
import {WebView} from 'react-native-webview';
import GradientNavgation from '@src/component/GradientNavgation';
import {Image, View, Text, StatusBar} from 'react-native';

class Index extends Component {
  render() {
    return (
      <>
        <GradientNavgation title={'摇一摇'} />
        <Text>待开发……</Text>
      </>
    );
  }
}
export default Index;
