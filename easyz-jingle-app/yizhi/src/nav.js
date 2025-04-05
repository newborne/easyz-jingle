import React from 'react';
import {NavigationContainer} from '@react-navigation/native';
import {createStackNavigator} from '@react-navigation/stack';
import {inject, observer} from 'mobx-react';
import Login from '@src/page/0_login/login';
import UserInfo from '@src/page/0_login/userInfo';
import Tabbar from '@src/tabbar';
import Demo from '@src/page/demo';
import MaterialType from '@src/page/1_material/materialType';
import News from '@src/page/1_material/news';
import Scrip from '@src/page/1_material/scrip';
import Near from '@src/page/4_users/recommendUser/near';
import Sway from '@src/page/4_users/recommendUser/sway';
import TestValue from '@src/page/4_users/recommendUser/testValue';
import MyVideo from '@src/page/5_info/myVideo';
import MyPost from '@src/page/5_info/myPost';
import Setting from '@src/page/5_info/setting';
import Feedback from '@src/page/5_info/feedback';
import UpdateUserInfo from '@src/page/5_info/updateUserInfo';
import Friend from '@src/page/5_info/friend';
import Shop from '@src/page/5_info/shop';
import UserDetail from '@src/page/4_users/userDetail';
import MaterialDetail from '@src/page/1_material/materialDetail';
import ArticleDetail from '@src/page/2_article/articleDetail';
import Comment from '@src/page/3_publish/comment';

const Stack = createStackNavigator();
@inject('RootStore')
@observer
class Nav extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      initialRouteName: this.props.RootStore.token ? 'Tabbar' : 'Login',
    };
  }
  render() {
    const {initialRouteName} = this.state;
    return (
      <NavigationContainer>
        <Stack.Navigator
          screenOptions={{headerShown: false}}
          initialRouteName={'Login'}>
          <Stack.Screen name="Demo" component={Demo} />
          <Stack.Screen name="Login" component={Login} />
          <Stack.Screen name="UserInfo" component={UserInfo} />
          <Stack.Screen name="Tabbar" component={Tabbar} />
          <Stack.Screen name="MaterialType" component={MaterialType} />
          <Stack.Screen name="News" component={News} />
          <Stack.Screen name="Scrip" component={Scrip} />
          <Stack.Screen name="Near" component={Near} />
          <Stack.Screen name="Sway" component={Sway} />
          <Stack.Screen name="TestValue" component={TestValue} />
          <Stack.Screen name="MyVideo" component={MyVideo} />
          <Stack.Screen name="MyPost" component={MyPost} />
          <Stack.Screen name="Feedback" component={Feedback} />
          <Stack.Screen name="Setting" component={Setting} />
          <Stack.Screen name="UpdateUserInfo" component={UpdateUserInfo} />
          <Stack.Screen name="Friend" component={Friend} />
          <Stack.Screen name="Shop" component={Shop} />
          <Stack.Screen name="UserDetail" component={UserDetail} />
          <Stack.Screen name="MaterialDetail" component={MaterialDetail} />
          <Stack.Screen name="ArticleDetail" component={ArticleDetail} />
          <Stack.Screen name="Comment" component={Comment} />
        </Stack.Navigator>
      </NavigationContainer>
    );
  }
}
export default Nav;
