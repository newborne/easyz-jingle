import React, {Component} from 'react';
import {StatusBar, View, ScrollView, Image, Text} from 'react-native';
import {BoxShadow} from 'react-native-shadow';
import {pxToDp} from '@src/util/pxToDp';
import GradientNavgation from '@src/component/GradientNavgation';
import SearchBox from '@src/component/SearchBox';
import TouchableScale from 'react-native-touchable-scale';
import LinearGradient from 'react-native-linear-gradient';
import Date from '@src/util/Date';
import {USERS_LIST, MATERIAL_TYPE} from '@src/util/Api';
import {NavigationContext} from '@react-navigation/native';
import Request from '@src/util/Request';
class Index extends Component {
  static contextType = NavigationContext;
  state = {
    txt: '',
    materialType: '20001',
    list1: [
      {
        courseSort: {
          logo: 'https://z3.ax1x.com/2021/06/09/2y4dKO.png',
          title: '爱国',
          materialType: '20001',
          content: '/积累经典短句\n/表达深刻主题',
        },
      },
      {
        courseSort: {
          logo: 'https://z3.ax1x.com/2021/06/09/2y4dKO.png',
          title: '诚信',
          materialType: '20003',
          content: '/解读海内外时事热点\n/丰富议论文论据',
        },
      },
      {
        courseSort: {
          logo: 'https://z3.ax1x.com/2021/06/09/2y4dKO.png',
          title: '敬业',
          materialType: '20002',
          content: '/解读各领域优秀人物\n/丰富议论文依据',
        },
      },
      {
        courseSort: {
          logo: 'https://z3.ax1x.com/2021/06/09/2y4dKO.png',
          title: '友善',
          materialType: '20004',
          content: '/解读文学、影视经典\n/拓展视野',
        },
      },
      {
        courseSort: {
          logo: 'https://z3.ax1x.com/2021/06/09/2y4dKO.png',
          title: '豪放',
          materialType: '20006',
          content: '/唐宋八大家\n/初唐四杰',
        },
      },
      {
        courseSort: {
          logo: 'https://z3.ax1x.com/2021/06/09/2y4dKO.png',
          title: '婉约',
          materialType: '20005',
          content: '/唐宋八大家\n/初唐四杰',
        },
      },
      {
        courseSort: {
          logo: 'https://z3.ax1x.com/2021/06/09/2y4dKO.png',
          title: '经典短句',
          content: '/积累经典短句\n/表达深刻主题',
        },
      },
      {
        courseSort: {
          logo: 'https://z3.ax1x.com/2021/06/09/2y4dKO.png',
          title: '时事',
          materialType: '20007',
          content: '/解读海内外时事热点\n/丰富议论文论据',
        },
      },
      {
        courseSort: {
          logo: 'https://z3.ax1x.com/2021/06/09/2y4dKO.png',
          title: '人物',
          content: '/解读各领域优秀人物\n/丰富议论文依据',
        },
      },
      {
        courseSort: {
          logo: 'https://z3.ax1x.com/2021/06/09/2y4dKO.png',
          title: '名著影视',
          content: '/解读文学、影视经典\n/拓展视野',
        },
      },
      {
        courseSort: {
          logo: 'https://z3.ax1x.com/2021/06/09/2y4dKO.png',
          title: '诗词歌赋',
          content: '/唐宋八大家\n/初唐四杰',
        },
      },
    ],
    list2: [
      // "id": "622cafc43bdf8863a3f0b560",
      // 	"materialRid": "1",
      // 	"tags": [
      // 		"爱国"
      // 	],
      // 	"text": "捐躯赴国难，视死忽如归。 曹植 《白马篇》",
      // 	"loveCount": 0,
      // 	"hasLoved": 0
    ],
  };
  componentDidMount() {
    this.getlist(this.state.materialType);
  }
  getlist = async materialType => {
    const url2 = MATERIAL_TYPE.replace(':materialType', materialType);
    const res2 = await Request.privateGet(url2);
    if (res2.ok) {
      const list2 = res2.data.records;
      this.setState({list2});
    }
    console.log(res1);
  };
  render() {
    const {list1, list2, txt} = this.state;
    const list3 = list2.filter(v => v.text.includes(txt));
    return (
      <>
        <GradientNavgation title="分类素材" />
        <View style={{paddingTop: pxToDp(10), backgroundColor: '#f7f7f7'}}>
          <SearchBox
            onChangeText={txt => this.setState({txt})}
            value={this.state.txt}
          />
        </View>
        <ScrollView
          style={{
            height: pxToDp(180),
            backgroundColor: '#f7f7f7',
          }}
          horizontal={true} //水平滚动开启
          showsHorizontalScrollIndicator={false} //是否显示滚动条
        >
          <View
            style={{
              flexDirection: 'row',
              flexWrap: 'wrap',
              justifyContent: 'space-between',
              padding: pxToDp(12),
            }}>
            {list1.map((v, i) => (
              <BoxShadow
                setting={{
                  width: pxToDp(80),
                  height: pxToDp(80),
                  color: '#000',
                  border: pxToDp(1),
                  radius: pxToDp(12),
                  opacity: 0.1,
                  x: 2,
                  y: 2,
                  style: {
                    justifyContent: 'center',
                    margin: pxToDp(8),
                    flexDirection: 'row',
                  },
                }}>
                <TouchableScale
                  onPress={() => this.getlist(v.courseSort.materialType)}
                  key={i}
                  style={{
                    height: pxToDp(80),
                    width: pxToDp(80),
                    backgroundColor: '#ffffff',
                    borderRadius: pxToDp(12),
                  }}>
                  <View>
                    <Text
                      style={{
                        color: '#666',
                        fontWeight: 'bold',
                        fontSize: pxToDp(16),
                        marginLeft: pxToDp(10),
                        marginTop: pxToDp(20),
                      }}>
                      {v.courseSort.title}
                    </Text>
                    <Text
                      numberOfLines={2}
                      style={{
                        color: '#666',
                        fontSize: pxToDp(7),
                        marginTop: pxToDp(5),
                        marginLeft: pxToDp(10),
                      }}>
                      {v.courseSort.content}
                    </Text>
                  </View>
                  <Image
                    source={{uri: v.courseSort.logo}}
                    style={{
                      width: pxToDp(24),
                      height: pxToDp(24),
                      position: 'absolute',
                      borderRadius: pxToDp(12),
                      right: pxToDp(0),
                      top: pxToDp(1),
                    }}
                  />
                </TouchableScale>
              </BoxShadow>
            ))}
          </View>
        </ScrollView>

        <ScrollView minHeight={pxToDp(600)}>
          <View
            style={{
              marginBottom: pxToDp(12),
              paddingLeft: pxToDp(4),
              paddingRight: pxToDp(4),
              paddingTop: pxToDp(8),
            }}>
            {list3.map((v, i) => (
              <TouchableScale
                friction={90} //
                tension={124} // These props are passed to the parent component (here TouchableScale)
                activeScale={0.95} //
                onPress={() =>
                  this.context.navigate('MaterialDetail', {
                    materialRid: v.materialRid,
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
                    width: '90%',
                    marginLeft: pxToDp(10),
                    padding: pxToDp(15),
                  }}>
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
                    {v.tags}
                  </Text>
                </View>
                <Image
                  source={{uri: 'https://z3.ax1x.com/2021/06/09/2y4dKO.png'}}
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
      </>
    );
  }
}

export default Index;
