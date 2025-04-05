import React, {Component} from 'react';
import {View} from 'react-native';
import {WebView} from 'react-native-webview';
import GradientNavgation from '@src/component/GradientNavgation';

class Index extends Component {
  state = {
    uri: 'https://blog.csdn.net/qq_41783491',
  };
  render() {
    const {uri} = this.state;
    return (
      <>
        <GradientNavgation title={'反馈'} />
        <WebView
          originWhitelist={['*']}
          source={{
            uri: uri,
          }}
        />
      </>
    );
  }
}
export default Index;
