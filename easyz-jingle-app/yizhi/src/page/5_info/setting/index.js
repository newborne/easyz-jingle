import React, {Component} from 'react';
import {TouchableOpacity, Text, View} from 'react-native';
import GradientNavgation from '@src/component/GradientNavgation';
import TouchableScale from 'react-native-touchable-scale';

import LinearGradient from 'react-native-linear-gradient';
import {inject, observer} from 'mobx-react';
import AsyncStorage from '@react-native-community/async-storage';
import {ListItem} from 'react-native-elements';
import {pxToDp} from '@src/util/pxToDp';
import Toast from '@src/util/Toast';
import {ActionSheet} from 'teaset';

@inject('UserStore')
@inject('RootStore')
@observer
class Index extends Component {
  logout = async () => {
    const tmplogout = async () => {
      console.log('执行退出');
      // 清除缓存
      await AsyncStorage.removeItem('userinfo');
      // 清除用户数据
      this.props.UserStore.clearUser();
      // 清除token数据
      this.props.RootStore.clearUserInfo();

      Toast.smile('退出成功', 2000);

      setTimeout(() => {
        this.props.navigation.navigate('Login');
      }, 2000);
    };

    const opts = [{title: '退出', onPress: tmplogout}];
    ActionSheet.show(opts, {title: '取消'});
  };
  render() {
    // const user = this.props.UserStore.user;
    return (
      <View>
        <GradientNavgation title={'设置'} />
        <View>
          <ListItem
            Component={TouchableScale}
            containerStyle={{
              borderRadius: pxToDp(24),
              margin: pxToDp(10),
            }}
            friction={90} //
            tension={124} // These props are passed to the parent component (here TouchableScale)
            activeScale={0.95} //
            linearGradientProps={{
              colors: ['#eee', '#fff'],
              start: {x: 0, y: 0},
              end: {x: 1, y: 1},
            }}
            ViewComponent={LinearGradient} // Only if no expo
            onPress={() => this.setState({showUserName: true})}>
            <ListItem.Content
              style={{flexDirection: 'row', justifyContent: 'space-between'}}>
              <ListItem.Title style={{color: '#000', fontWeight: 'bold'}}>
                修改手机号
              </ListItem.Title>
              <ListItem.Title style={{color: '#000', fontWeight: 'bold'}}>
                {this.props.RootStore.mobile}
              </ListItem.Title>
            </ListItem.Content>
          </ListItem>
        </View>

        <View style={{marginTop: pxToDp(30)}}>
          <TouchableOpacity
            onPress={this.logout}
            style={{
              width: '30%',
              alignSelf: 'center',
              alignItems: 'center',
              justifyContent: 'center',
              height: pxToDp(40),
              borderRadius: pxToDp(20),
              backgroundColor: '#36cfc9',
            }}>
            <Text style={{color: '#fff'}}>退出登录</Text>
          </TouchableOpacity>
        </View>
      </View>
    );
  }
}
export default Index;
