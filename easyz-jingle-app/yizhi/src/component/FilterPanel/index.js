import React, {Component} from 'react';
import {View, Text, TouchableOpacity} from 'react-native';
import IconFont from '@src/component/IconFont';
import {pxToDp} from '@src/util/pxToDp';
import {Slider} from 'react-native-elements';
import GradientButton from '@src/component/GradientButton';
class Index extends Component {
  constructor(props) {
    super(props);
    this.state = JSON.parse(JSON.stringify(this.props.params));
  }

  // 选择性别
  chooeseSex = sex => {
    if (sex === this.state.sex) {
      sex = '';
    }
    this.setState({sex});
  };

  // 点击确定 要执行的方法
  handleSubmitFilter = () => {
    this.props.onSubmitFilter(this.state);
    this.props.onClose();
  };

  render() {
    const {sex, distance} = this.state;
    return (
      <View
        style={{
          position: 'absolute',
          width: '100%',
          height: pxToDp(450),
          left: 0,
          bottom: 0,
          backgroundColor: '#fff',
          opacity: 0.85,
          paddingLeft: pxToDp(10),
          paddingRight: pxToDp(10),
          borderTopLeftRadius: pxToDp(32),
          borderTopRightRadius: pxToDp(32),
        }}>
        {/* 1.0 标题 开始 */}
        <View
          style={{
            alignItems: 'center',
          }}>
          <TouchableOpacity
            onPress={this.props.onClose}
            style={{margin: pxToDp(12), width: pxToDp(32), height: pxToDp(32)}}>
            <IconFont
              style={{fontSize: pxToDp(32), color: '#666'}}
              name="iconDown"
            />
          </TouchableOpacity>

          <Text style={{color: '#666', fontSize: pxToDp(24)}}>筛选</Text>
        </View>
        {/* 1.0 标题 结束 */}
        {/* 2.0 性别 开始 */}
        <View
          style={{
            marginTop: pxToDp(24),
            justifyContent: 'space-around',
            width: pxToDp(216),
            alignSelf: 'center',
            flexDirection: 'row',
          }}>
          <View alignItems={'center'}>
            <TouchableOpacity
              onPress={this.chooeseSex.bind(this, 'man')}
              style={{
                width: pxToDp(60),
                height: pxToDp(60),
                borderRadius: pxToDp(30),
                backgroundColor: sex === 'man' ? '#37DC8A' : '#666',
                justifyContent: 'center',
                alignItems: 'center',
              }}>
              <IconFont
                name="iconMan"
                style={{fontSize: pxToDp(36), color: '#fff'}}
              />
            </TouchableOpacity>
            <Text style={{color: '#666', marginTop: pxToDp(4)}}>男</Text>
          </View>

          <View alignItems={'center'}>
            <TouchableOpacity
              onPress={this.chooeseSex.bind(this, 'woman')}
              style={{
                width: pxToDp(60),
                height: pxToDp(60),
                borderRadius: pxToDp(30),
                backgroundColor: sex === 'woman' ? '#37DC8A' : '#666',
                justifyContent: 'center',
                alignItems: 'center',
              }}>
              <IconFont
                name="iconWoman"
                style={{fontSize: pxToDp(36), color: '#fff'}}
              />
            </TouchableOpacity>
            <Text style={{color: '#666', marginTop: pxToDp(4)}}>女</Text>
          </View>
        </View>
        {/* 2.0 性别 结束 */}

        {/* 3.0 距离 开始 */}
        <View style={{marginTop: pxToDp(10)}}>
          <Slider
            value={distance}
            minimumValue={0}
            maximumValue={50000}
            step={100}
            thumbTintColor={'#37DC8A'}
            thumbStyle={{height: pxToDp(40), width: pxToDp(16)}}
            onValueChange={distance => this.setState({distance})}
          />
          <Text
            style={{color: '#666', fontSize: pxToDp(18), alignSelf: 'center'}}>
            距离:{distance || 0} M
          </Text>
        </View>
        {/* 3.0 距离 结束 */}
        <GradientButton
          onPress={this.handleSubmitFilter}
          style={{
            position: 'absolute',
            bottom: '20%',
            width: '50%',
            alignSelf: 'center',
            height: pxToDp(48),
            borderRadius: pxToDp(24),
          }}>
          搜索
        </GradientButton>
      </View>
    );
  }
}
export default Index;
