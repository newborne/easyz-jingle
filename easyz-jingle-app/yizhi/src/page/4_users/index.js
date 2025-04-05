import React from 'react';
import ScrollableTabView from 'react-native-top-tab-view';
import TopTabBar from '@src/component/TopTabBar';
import RecommendUser from './recommendUser';
import Message from './message';
import UsersList from './usersList';

export default () => {
  return (
    <ScrollableTabView initialPage={1} renderTabBar={() => <TopTabBar />}>
      <RecommendUser tabLabel="发现" />
      <Message tabLabel="消息" />
      <UsersList tabLabel="列表" />
    </ScrollableTabView>
  );
};
