import React, {Component} from 'react';
import {View, Text, TouchableOpacity, Image} from 'react-native';
import SearchBox from '@src/component/SearchBox';
import {pxToDp} from '@src/util/pxToDp';
import IconFont from '@src/component/IconFont';
import LinearGradient from 'react-native-linear-gradient';
import Request from '@src/util/Request';
import Toast from '@src/util/Toast';
import {NavigationContext} from '@react-navigation/native';

import {BASE_URI, USERS_UNFOLLOW_FRIENDID} from '@src/util/Api';
class Index extends Component {
  static contextType = NavigationContext;
  state = {
    txt: '',
  };
  // 取消关注
  unFollow = async id => {
    // :id :type
    const url = USERS_UNFOLLOW_FRIENDID.replace(':friendId', id);
    const res = await Request.privateGet(url);

    Toast.smile('取消关注成功');

    // 刷新数据
    this.props.getList();
  };
  render() {
    console.log(this.props);
    const {txt} = this.state;
    const {followList} = this.props;
    // 筛选后的数组
    const list = followList.filter(v => v.nickName.includes(txt));
    return (
      <View style={{flex: 1, backgroundColor: '#fff'}}>
        <View style={{backgroundColor: '#eee', padding: pxToDp(10)}}>
          <SearchBox
            onChangeText={txt => this.setState({txt})}
            value={this.state.txt}
            style={{marginTop: pxToDp(10)}}
          />
        </View>
        {list.map((user, i) => (
          <View
            key={i}
            style={{
              flexDirection: 'row',
              paddingTop: pxToDp(15),
              paddingBottom: pxToDp(15),
              paddingRight: pxToDp(15),
              borderBottomColor: '#ccc',
              borderBottomWidth: pxToDp(1),
            }}>
            {/* 图片 */}
            <View style={{paddingLeft: pxToDp(15), paddingRight: pxToDp(15)}}>
              <TouchableOpacity
                onPress={() =>
                  this.context.navigate('UserDetail', {id: user.id})
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
                      user.sex === 'woman' ? 'iconWomanMini' : 'iconManMini'
                    }
                  />
                  <Text style={{color: '#555'}}>{user.age}岁</Text>
                </View>
              </View>

              <View style={{flexDirection: 'row'}}>
                <Text style={{color: '#555', marginRight: pxToDp(5)}}>
                  {user.edu}
                </Text>
                <Text style={{color: '#555', marginRight: pxToDp(5)}}>|</Text>
                <Text style={{color: '#555', marginRight: pxToDp(5)}}>
                  {user.city}
                </Text>
                <Text style={{color: '#555', marginRight: pxToDp(5)}}>|</Text>
                <Text style={{color: '#555', marginRight: pxToDp(5)}}>
                  相似度：{user.similarity}
                </Text>
              </View>
            </View>
            {/* 按钮  */}
            <TouchableOpacity
              style={{
                flexDirection: 'row',
                position: 'absolute',
                right: pxToDp(10),
                top: pxToDp(10),
                backgroundColor: 'red',
                width: pxToDp(72),
                height: pxToDp(32),
                borderRadius: pxToDp(16),
                flexDirection: 'row',
                alignItems: 'center',
                justifyContent: 'space-evenly',
              }}
              onPress={() => this.unFollow(user.id)}>
              <IconFont
                style={{color: '#fff', fontSize: pxToDp(16)}}
                name="iconFollow"></IconFont>
              <Text style={{color: '#fff', fontSize: pxToDp(10)}}>
                取消关注
              </Text>
            </TouchableOpacity>
          </View>
        ))}
      </View>
    );
  }
}
export default Index;
