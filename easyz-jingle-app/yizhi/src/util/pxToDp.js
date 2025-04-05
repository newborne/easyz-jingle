import {Dimensions} from 'react-native';

export const screenWidth = Dimensions.get('window').width;
export const screenHeight = Dimensions.get('window').height;
/**
 * px转dp，设计稿宽度411
 * @param {Number} elePx
 */
export const pxToDp = elePx => (screenWidth * elePx) / 411;
