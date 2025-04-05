import React, {Component} from 'react';
import {View} from 'react-native';
import Nav from './src/nav';
import Geo from './src/util/Geo';
import RootStore from './src/store/rootStore';
import UserStore from './src/store/userStore';

import {Provider} from 'mobx-react';
import AsyncStorage from '@react-native-community/async-storage';

class App extends Component {
  state = {
    isInitGeo: false,
  };
  async componentDidMount() {
    // 获取缓存中的用户数据
    const strUserInfo = await AsyncStorage.getItem('userInfo');
    const userInfo = strUserInfo ? JSON.parse(strUserInfo) : {};
    if (userInfo.token) {
      RootStore.setUserInfo(userInfo.mobile, userInfo.token, userInfo.userId);
    }
    // 高德地图初始化
    await Geo.initGeo();
    this.setState({isInitGeo: true});
  }
  render() {
    return (
      <View style={{flex: 1}}>
        <Provider RootStore={RootStore} UserStore={UserStore}>
          {this.state.isInitGeo ? <Nav /> : <></>}
        </Provider>
      </View>
    );
  }
}
export default App;
