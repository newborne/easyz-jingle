import React, {Component} from 'react';
import {
  Text,
  View,
  StyleSheet,
  Button,
  TouchableOpacity,
  Image,
} from 'react-native';
import Video from 'react-native-video';

class Index extends Component {
  state = {
    paused: true,
    uri: 'http://10.0.2.2:8888/group1/M00/00/00/wKhvgGJpSAuABOo9AGFXKqItf0Q004.mp4',
    // uri: 'https://video.699pic.com/videos/73/92/43/b_mPEcRsUxTkE91597739243.mp4',
  };

  render() {
    const {uri} = this.state;
    return (
      <>
        <Text>待开发……</Text>
        <View>
          <Video
            source={{uri: uri}} // Can be a URL or a local file.
            ref={ref => {
              this.player = ref;
            }} // Store reference
            onEnd={() => {}}
            resizeMode="contain"
            posterResizeMode="cover"
            onLoad={this.onLoad}
            volume={5}
            muted={false}
            onError={this.onError}
            onLoadStart={this.onLoadStart}
            style={{width: '100%', height: '80%'}}
            paused={this.state.paused}
          />
          {/* 屏幕中央 播放按钮 */}
          {this.state.paused ? (
            <View
              style={{
                position: 'absolute',
                width: '100%',
                height: '80%',
                justifyContent: 'center',
                alignItems: 'center',
              }}>
              <TouchableOpacity
                onPress={() => {
                  this.setState({
                    paused: !this.state.paused,
                  });
                }}>
                <Image
                  source={require('@src/res/image/play.png')}
                  resizeMode={'contain'}
                  style={{width: 60, height: 60}}
                />
              </TouchableOpacity>
            </View>
          ) : null}
        </View>
      </>
    );
  }
}
export default Index;
