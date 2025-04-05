import React, {Component} from 'react';
import {WebView} from 'react-native-webview';
import GradientNavgation from '@src/component/GradientNavgation';

class Index extends Component {
  state = {
    uri: 'https://www.kuaishou.com/short-video/3xkr5yjj8fhff72?authorId=3x2348n4ev33a2q&streamSource=search&area=searchxxnull&searchKey=%E4%BD%9C%E6%96%87',
  };

  render() {
    const {uri} = this.state;
    return (
      <>
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
