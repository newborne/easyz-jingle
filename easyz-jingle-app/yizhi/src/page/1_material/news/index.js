import React, {Component} from 'react';
import {WebView} from 'react-native-webview';
import GradientNavgation from '@src/component/GradientNavgation';

class Index extends Component {
  state = {
    uri: 'https://tophub.today/n/KqndgxeLl9',
  };
  render() {
    const {uri} = this.state;
    return (
      <>
        <GradientNavgation title={'今日热点'} />
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
