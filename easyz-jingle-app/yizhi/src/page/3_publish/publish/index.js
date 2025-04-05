import React from 'react';
import ScrollableTabView from 'react-native-top-tab-view';
import SecondTabBar from '@src/component/SecondTabBar';
import PostPublish from './postPublish';
import VideoPublish from './videoPublish';

export default () => {
  return (
    <ScrollableTabView initialPage={0} renderTabBar={() => <SecondTabBar />}>
      <PostPublish tabLabel="文章" />
      <VideoPublish tabLabel="短视频" />
    </ScrollableTabView>
  );
};
