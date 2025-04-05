import React from 'react';
import ScrollableTabView from 'react-native-top-tab-view';

import RecommendPost from './recommendPost';
import FriendPost from './friendPost';

import SecondTabBar from '@src/component/SecondTabBar';

export default () => {
  return (
    <ScrollableTabView initialPage={0} renderTabBar={() => <SecondTabBar />}>
      <RecommendPost tabLabel="推荐" />
      <FriendPost tabLabel="关注" />
    </ScrollableTabView>
  );
};
