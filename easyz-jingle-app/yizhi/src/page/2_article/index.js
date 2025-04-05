import React from 'react';
import ScrollableTabView from 'react-native-top-tab-view';
import TopTabBar from '@src/component/TopTabBar';
import RecommendArticle from './recommendArticle';
import ArticleType from './articleType';

export default () => {
  return (
    <ScrollableTabView initialPage={0} renderTabBar={() => <TopTabBar />}>
      <RecommendArticle tabLabel="推荐" />
      <ArticleType tabLabel="分类" />
    </ScrollableTabView>
  );
};
