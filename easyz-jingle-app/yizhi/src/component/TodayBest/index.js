import React, {Component} from 'react';
import {View, Text, Image} from 'react-native';
import {pxToDp} from '@src/util/pxToDp';
import IconFont from '@src/component/IconFont';
import Request from '@src/util/Request';
import {USERS_RECOMMEND_USER_LIST, USERS_TODAYBEST} from '@src/util/Api';
class Index extends Component {
  state = {
    todayBest: {
      // id: 8,
      // logo: 'https://z3.ax1x.com/2021/05/22/gqLnWq.png',
      // userName: 'yizhiuser_8',
      // sex: 'man',
      // age: 15,
      // tags: ['六年级，年龄相近'],
      // similarity: 97,
    },
  };
  async componentDidMount() {
    const res = await Request.privateGet(USERS_TODAYBEST);
    this.setState({todayBest: res.data});
    console.log(todayBest.tags);
  }
  render() {
    const {todayBest} = this.state;
    return (
      <View
        style={{
          flexDirection: 'row',
          padding: pxToDp(12),
          backgroundColor: '#ffffff',
          borderRadius: pxToDp(18),
        }}>
        <View
          style={{
            width: pxToDp(24),
            height: pxToDp(108),
            backgroundColor: '#36cfc9',
            justifyContent: 'center',
            borderRadius: pxToDp(12),
          }}>
          <Text
            style={{color: '#fff', fontSize: pxToDp(12), textAlign: 'center'}}>
            今日最佳
          </Text>
        </View>
        {/* 左边图片 开始 */}
        <View style={{position: 'relative', paddingLeft: pxToDp(12)}}>
          <Image
            style={{width: pxToDp(108), height: pxToDp(108)}}
            source={{uri: todayBest.logo}}
          />
        </View>
        {/* 左边图片 结束 */}
        {/* 右边内容 开始 */}
        <View style={{flex: 1, flexDirection: 'row', paddingLeft: pxToDp(12)}}>
          <View style={{flex: 2, justifyContent: 'space-around'}}>
            <View style={{flexDirection: 'row', alignItems: 'center'}}>
              <Text style={{color: '#666'}}>{todayBest.nickName}</Text>
            </View>
            <View style={{flexDirection: 'row'}}>
              <IconFont
                style={{
                  fontSize: pxToDp(18),
                  marginLeft: pxToDp(5),
                  marginRight: pxToDp(5),
                  color: todayBest.sex === 'woman' ? '#36cfc9' : '#37DC8A',
                }}
                name={
                  todayBest.sex === 'woman' ? 'iconWomanMini' : 'iconManMini'
                }
              />
              <Text style={{color: '#666'}}>{todayBest.age}岁</Text>
            </View>
            <View style={{flexDirection: 'row'}}>
              <Text>{todayBest.tags}</Text>
              {/* {todayBest.tags((v, i) => (
                <Text style={{color: '#555', marginRight: pxToDp(5)}} key={i}>
                  {v} |
                </Text>
              ))} */}
            </View>
          </View>
          <View
            style={{flex: 1, alignItems: 'center', justifyContent: 'center'}}>
            <View
              style={{
                position: 'relative',
                alignItems: 'center',
                justifyContent: 'center',
              }}>
              <IconFont
                name="iconSimilarity"
                style={{fontSize: pxToDp(56), color: '#37DC8A'}}
              />
              <Text
                style={{
                  position: 'absolute',
                  color: '#fff',
                  fontSize: pxToDp(20),
                  fontWeight: 'bold',
                }}>
                {todayBest.similarity}
              </Text>
            </View>
            <Text style={{color: '#36cfc9', fontSize: pxToDp(13)}}>相似度</Text>
          </View>
        </View>
        {/* 右边内容 结束 */}
      </View>
    );
  }
}
export default Index;
