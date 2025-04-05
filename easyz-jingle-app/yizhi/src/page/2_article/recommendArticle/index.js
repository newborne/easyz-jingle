import React, {Component} from 'react';
import {Image, ScrollView, Text, TouchableOpacity, View} from 'react-native';
import TouchableScale from 'react-native-touchable-scale';
import {pxToDp} from '@src/util/pxToDp';
import {USERS_LIST, ARTICLE_RECOMMEND_V1} from '@src/util/Api';
import {NavigationContext} from '@react-navigation/native';
import Request from '@src/util/Request';

class Index extends Component {
  static contextType = NavigationContext;
  state = {
    list: [],
  };
  componentDidMount() {
    this.getlist();
  }
  getlist = async () => {
    const res1 = await Request.privateGet(ARTICLE_RECOMMEND_V1);
    if (res1.ok) {
      const list = res1.data.records;
      this.setState({list});
    }
    console.log(res1);
  };
  render() {
    const {list} = this.state;
    return (
      <ScrollView>
        <View
          style={{
            marginBottom: pxToDp(12),
            paddingLeft: pxToDp(4),
            paddingRight: pxToDp(4),
            paddingTop: pxToDp(8),
            backgroundColor: '#eee',
          }}>
          {list.map((v, i) => (
            <TouchableScale
              friction={90} //
              tension={124} // These props are passed to the parent component (here TouchableScale)
              activeScale={0.95} //
              onPress={() =>
                this.context.navigate('ArticleDetail', {
                  articleRid: v.articleRid,
                })
              }
              key={i}
              style={{
                flexDirection: 'row',
                padding: pxToDp(12),
                backgroundColor: '#ffffff',
                borderRadius: pxToDp(18),
                marginBottom: pxToDp(4),
                marginLeft: pxToDp(4),
                marginRight: pxToDp(4),
              }}>
              <View
                style={{
                  padding: pxToDp(15),
                  width: '93%',
                }}>
                <Text
                  style={{
                    color: '#666',
                    fontWeight: 'bold',
                    alignSelf: 'center',
                  }}>
                  {v.title}
                </Text>
                <Text numberOfLines={7} style={{color: '#666'}}>
                  {v.text}
                </Text>
              </View>
              <View
                style={{
                  width: pxToDp(24),
                  backgroundColor: '#36cfc9',
                  justifyContent: 'center',
                  alignSelf: 'center',
                  borderRadius: pxToDp(12),
                }}>
                <Text
                  style={{
                    marginTop: pxToDp(12),
                    marginBottom: pxToDp(12),
                    color: '#fff',
                    fontSize: pxToDp(12),
                    textAlign: 'center',
                  }}>
                  {v.tags[0] + '\n--\n' + v.tags[1] + '\n--\n' + v.tags[2]}
                </Text>
              </View>
              <Image
                source={{uri: 'https://z3.ax1x.com/2021/06/09/2y5BlV.png'}}
                style={{
                  width: pxToDp(36),
                  height: pxToDp(36),
                  position: 'absolute',
                  left: pxToDp(2),
                  top: pxToDp(2),
                }}
              />
            </TouchableScale>
          ))}
        </View>
      </ScrollView>
    );
  }
}
export default Index;
