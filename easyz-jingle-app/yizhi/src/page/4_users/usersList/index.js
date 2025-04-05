import React, {Component} from 'react';
import {
  StatusBar,
  View,
  ScrollView,
  TouchableOpacity,
  Image,
  Text,
} from 'react-native';
import {BoxShadow} from 'react-native-shadow';
import {pxToDp} from '@src/util/pxToDp';
import GradientNavgation from '@src/component/GradientNavgation';
import SearchBox from '@src/component/SearchBox';
import TouchableScale from 'react-native-touchable-scale';
import IconFont from '@src/component/IconFont';
import {USERS_LIST} from '@src/util/Api';
import {NavigationContext} from '@react-navigation/native';
import Request from '@src/util/Request';
class Index extends Component {
  static contextType = NavigationContext;
  state = {
    txt: '',
    // 接口要的数据
    params: {
      page: 1,
      pagesize: 10,
      sex: '',
      lastLogin: '',
      city: '',
      edu: '',
    },
    // 朋友 数组
    userList: [],
  };
  componentDidMount() {
    this.getUserList();
  }
  getUserList = async () => {
    const res1 = await Request.privateGet(USERS_LIST);
    if (res1.ok) {
      const userList = res1.data.records;
      this.setState({userList});
    }

    console.log(res1);
  };
  render() {
    const {userList, txt} = this.state;
    const list = userList.filter(v => v.nickName.includes(txt));
    return (
      <ScrollView>
        <StatusBar backgroundColor="transparent" translucent={true} />
        <View style={{backgroundColor: '#f7f7f7'}}>
          <View style={{marginTop: pxToDp(10)}}>
            <SearchBox
              onChangeText={txt => this.setState({txt})}
              value={this.state.txt}
            />
            <View style={{paddingLeft: pxToDp(4), paddingRight: pxToDp(4)}}>
              {list.map((user, i) => (
                <View
                  key={i}
                  style={{
                    flexDirection: 'row',
                    paddingTop: pxToDp(15),
                    paddingBottom: pxToDp(15),
                    paddingRight: pxToDp(15),
                    borderBottomColor: '#ccc',
                    borderBottomWidth: pxToDp(0.5),
                  }}>
                  {/* 图片 */}
                  <View
                    style={{paddingLeft: pxToDp(15), paddingRight: pxToDp(15)}}>
                    <TouchableOpacity
                      onPress={() =>
                        this.context.navigate('UserDetail', {id: user.userId})
                      }>
                      <Image
                        style={{
                          width: pxToDp(50),
                          height: pxToDp(50),
                          borderRadius: pxToDp(25),
                        }}
                        source={{uri: user.logo}}
                      />
                    </TouchableOpacity>
                  </View>
                  {/* 名称 */}
                  <View style={{flex: 2, justifyContent: 'space-around'}}>
                    <View style={{flexDirection: 'row', alignItems: 'center'}}>
                      <Text
                        style={{
                          color: '#666',
                          fontSize: pxToDp(17),
                        }}>
                        {user.nickName}
                      </Text>
                      <View
                        style={{
                          flexDirection: 'row',
                          backgroundColor: '#fff',
                          borderRadius: pxToDp(8),
                          paddingLeft: pxToDp(3),
                          paddingRight: pxToDp(3),
                          marginLeft: pxToDp(15),
                        }}>
                        <IconFont
                          style={{
                            marginLeft: pxToDp(5),
                            marginRight: pxToDp(5),
                            fontSize: pxToDp(18),
                            color: user.sex === 'woman' ? '#36cfc9' : '#37DC8A',
                          }}
                          name={
                            user.sex === 'woman'
                              ? 'iconWomanMini'
                              : 'iconManMini'
                          }
                        />
                        <Text style={{color: '#555'}}>{user.age}岁</Text>
                      </View>
                      <Text style={{color: '#555', marginLeft: pxToDp(24)}}>
                        {user.city}
                      </Text>
                    </View>
                  </View>
                </View>
              ))}
            </View>
          </View>
        </View>
      </ScrollView>
    );
  }
}

export default Index;
