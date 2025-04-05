import React, {Component} from 'react';
import {View, Text, Image, TouchableOpacity} from 'react-native';
import {pxToDp} from '@src/util/pxToDp';
import IconFont from '@src/component/IconFont';
import Date from '@src/util/Date';
import TouchableScale from 'react-native-touchable-scale';
import {
  BASE_URI,
  MESSAGE_ANNOUNCEMENT,
  MESSAGE_COMMENT_TYPE,
} from '@src/util/Api';
import Request from '@src/util/Request';
class Index extends Component {
  state = {
    list: [
      {
        // "id": "1",
        // "title": "公告1",
        // "description": "公告测试",
        // "created": "2022-03-09 18:54:53"
      },
    ],
  };
  componentDidMount() {
    this.getConversations();
  }

  getConversations = async () => {
    const res1 = await Request.privateGet(MESSAGE_ANNOUNCEMENT);
    if (res1.ok) {
      const list = res1.data.records;
      this.setState({list});
    }
  };

  getMessage = async type => {
    const url1 = MESSAGE_COMMENT_TYPE.replace(':commentType', type);
    const res1 = await Request.privateGet(url1);
    if (res1.ok) {
      const list = res1.data.records;
      this.setState({list});
    }
  };

  render() {
    const {list} = this.state;
    return (
      <View style={{flex: 1, backgroundColor: '#f7f7f7'}}>
        <View
          style={{
            height: pxToDp(128),
            backgroundColor: '#fff',
            flexDirection: 'row',
            alignItems: 'center',
            paddingLeft: pxToDp(30),
            paddingRight: pxToDp(30),
            justifyContent: 'space-between',
          }}>
          <TouchableScale
            style={{alignItems: 'center'}}
            onPress={this.getConversations}>
            <View
              style={{
                width: pxToDp(60),
                height: pxToDp(60),
                borderRadius: pxToDp(30),
                backgroundColor: '#ebc969',
                alignItems: 'center',
                justifyContent: 'center',
              }}>
              <IconFont
                name="iconAnnouncement"
                style={{fontSize: pxToDp(32), color: '#f7f7f7'}}
              />
            </View>
            <Text style={{color: '#666'}}>公告</Text>
          </TouchableScale>
          <TouchableScale
            style={{alignItems: 'center'}}
            onPress={() => this.getMessage('1')}>
            <View
              style={{
                width: pxToDp(60),
                height: pxToDp(60),
                borderRadius: pxToDp(30),
                backgroundColor: '#ff5314',
                alignItems: 'center',
                justifyContent: 'center',
              }}>
              <IconFont
                style={{color: '#f7f7f7', fontSize: pxToDp(32)}}
                name="iconLike"
              />
            </View>
            <Text style={{color: '#666'}}>点赞</Text>
          </TouchableScale>
          <TouchableScale
            style={{alignItems: 'center'}}
            onPress={() => this.getMessage('3')}>
            <View
              style={{
                width: pxToDp(60),
                height: pxToDp(60),
                borderRadius: pxToDp(30),
                backgroundColor: '#2fb4f9',
                alignItems: 'center',
                justifyContent: 'center',
              }}>
              <IconFont
                style={{color: '#f7f7f7', fontSize: pxToDp(32)}}
                name="iconComment"
              />
            </View>
            <Text style={{color: '#666'}}>评论</Text>
          </TouchableScale>
          <TouchableScale
            style={{alignItems: 'center'}}
            onPress={() => this.getMessage('4')}>
            <View
              style={{
                width: pxToDp(60),
                height: pxToDp(60),
                borderRadius: pxToDp(30),
                backgroundColor: '#1adbde',
                alignItems: 'center',
                justifyContent: 'center',
              }}>
              <IconFont
                style={{color: '#f7f7f7', fontSize: pxToDp(32)}}
                name="iconLove"
              />
            </View>
            <Text style={{color: '#666'}}>喜欢</Text>
          </TouchableScale>
        </View>
        <View>
          {list.map((v, i) => (
            <TouchableOpacity
              key={i}
              style={{
                padding: pxToDp(15),
                flexDirection: 'row',
                borderBottomWidth: pxToDp(0.5),
                borderBottomColor: '#ccc',
              }}>
              <View>
                <Image
                  source={{uri: v.title}}
                  style={{
                    width: pxToDp(54),
                    height: pxToDp(54),
                    borderRadius: pxToDp(27),
                  }}
                />
              </View>
              <View
                style={{
                  justifyContent: 'space-evenly',
                  paddingLeft: pxToDp(15),
                }}>
                <Text style={{color: '#666'}}>{v.title}</Text>
                <Text style={{color: '#666'}}>{v.description}</Text>
              </View>
              <View
                style={{
                  justifyContent: 'space-evenly',
                  flex: 1,
                  alignItems: 'flex-end',
                }}>
                <Text style={{color: '#666'}}>{Date(v.created).fromNow()}</Text>
                <View
                  style={{
                    width: pxToDp(20),
                    height: pxToDp(20),
                    borderRadius: pxToDp(10),
                    backgroundColor: 'red',
                    alignItems: 'center',
                    justifyContent: 'center',
                  }}>
                  <Text style={{color: '#f7f7f7'}}>{v.title}</Text>
                </View>
              </View>
            </TouchableOpacity>
          ))}
        </View>
      </View>
    );
  }
}

export default Index;
