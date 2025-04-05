import React, {Component} from 'react';
import ScrollableTabView from 'react-native-top-tab-view';
import Fan from './fan';
import Follow from './follow';
import MutualFollow from './mutualFollow';
import Visitor from './visitor';
import Request from '@src/util/Request';
import {USERS_FOLLOW_LIST_TYPE} from '@src/util/Api';
import MultiHead from '@src/component/MultiHead';

class Index extends Component {
  state = {
    // 关注
    followList: [],
    // 粉丝
    fanList: [],
    // 互相关注
    mutualFollowList: [],
  };

  componentDidMount() {
    this.getList();
  }

  getList = async () => {
    const url1 = USERS_FOLLOW_LIST_TYPE.replace(':type', 'follow');
    const res1 = await Request.privateGet(url1);
    const url2 = USERS_FOLLOW_LIST_TYPE.replace(':type', 'fan');
    const res2 = await Request.privateGet(url2);
    const url3 = USERS_FOLLOW_LIST_TYPE.replace(':type', 'mutualFollow');
    const res3 = await Request.privateGet(url3);
    if (res1.ok) {
      const followList = res1.data.records;
      this.setState({followList});
    }
    if (res2.ok) {
      const fanList = res2.data.records;
      this.setState({fanList});
    }
    if (res3.ok) {
      const mutualFollowList = res3.data.records;
      this.setState({mutualFollowList});
    }

    console.log(res1);
  };

  render() {
    const {followList, fanList, mutualFollowList} = this.state;
    const index = this.props.route.params || 0;
    return (
      <ScrollableTabView initialPage={index} renderTabBar={() => <MultiHead />}>
        <MutualFollow
          getList={this.getList}
          mutualFollowList={mutualFollowList}
          tabLabel="互相关注"
        />
        <Follow
          getList={this.getList}
          followList={followList}
          tabLabel="关注"
        />
        <Fan getList={this.getList} fanList={fanList} tabLabel="粉丝" />
        <Visitor tabLabel="访客" />
      </ScrollableTabView>
    );
  }
}

export default Index;
