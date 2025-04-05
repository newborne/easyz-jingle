import React from 'react';
import ScrollableTabView from 'react-native-top-tab-view';
import TopTabBar from '@src/component/TopTabBar';
import Post from './post';
import Video from './video';
import Publish from './publish';
import LinearGradient from 'react-native-linear-gradient';
import {TouchableOpacity} from 'react-native';
import IconFont from '@src/component/IconFont';
import {pxToDp} from '@src/util/pxToDp';

export default () => {
  return (
    <ScrollableTabView initialPage={1} renderTabBar={() => <TopTabBar />}>
      <Publish tabLabel="发布" />
      <Post tabLabel="文章" />
      <Video tabLabel="短视频" />
    </ScrollableTabView>
  );
};
