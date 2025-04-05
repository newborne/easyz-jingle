module.exports = {
  presets: ['module:metro-react-native-babel-preset'],
  plugins: [
    ['@babel/plugin-proposal-decorators', {legacy: true}],
    // 绝对路径插件
    [
      'babel-plugin-root-import',
      {
        rootPathPrefix: '@src',
        rootPathSuffix: 'src',
      },
    ],
    // 绝对路径插件
  ],
};
