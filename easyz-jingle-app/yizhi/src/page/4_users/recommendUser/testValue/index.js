import React, {Component} from 'react';
import {WebView} from 'react-native-webview';
import GradientNavgation from '@src/component/GradientNavgation';

class Index extends Component {
  state = {
    uri: 'https://www.d1xz.net/test/quwei/art509528.aspx',
  };
  render() {
    const {uri} = this.state;
    return (
      <>
        <GradientNavgation title={'测文值'} />
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
