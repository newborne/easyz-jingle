import React, {Component} from 'react';
import {WebView} from 'react-native-webview';
import GradientNavgation from '@src/component/GradientNavgation';

class Index extends Component {
  state = {
    uri: 'https://uland.taobao.com/semm/tbsearch?refpid=mm_26632258_3504122_32554087&keyword=%E6%B7%98%E5%AF%B6&clk1=ce5ab4bae35657cffa6950c920536688&upsId=ce5ab4bae35657cffa6950c920536688#q=%E4%BD%9C%E6%96%87',
  };
  render() {
    const {uri} = this.state;
    return (
      <>
        <GradientNavgation title={'周边好物'} />
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
