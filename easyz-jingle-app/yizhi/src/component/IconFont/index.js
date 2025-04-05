import React from 'react';
import {Text} from 'react-native';
import IconMap from '@src/res/font/iconMap';

const Index = props => (
  <Text
    onPress={props.onPress}
    style={{fontFamily: 'iconfont', ...props.style}}>
    {IconMap[props.name]}
  </Text>
);
export default Index;
