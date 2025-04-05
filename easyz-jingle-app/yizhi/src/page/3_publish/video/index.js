import React from 'react';
import ScrollableTabView from 'react-native-top-tab-view';

import RecommendVideo from './recommendVideo';
import FriendVideo from './friendVideo';
import {TouchableOpacity} from 'react-native';
import LinearGradient from 'react-native-linear-gradient';
import IconFont from '@src/component/IconFont';
import {pxToDp} from '@src/util/pxToDp';
import SecondTabBar from '@src/component/SecondTabBar';

export default () => {
  return (
    <ScrollableTabView initialPage={0} renderTabBar={() => <SecondTabBar />}>
      <RecommendVideo tabLabel="推荐" />
      <FriendVideo tabLabel="关注" />
    </ScrollableTabView>
  );
};
