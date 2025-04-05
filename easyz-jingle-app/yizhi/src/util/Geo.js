import {PermissionsAndroid, Platform} from 'react-native';
import {Geolocation, init} from 'react-native-amap-geolocation';
import axios from 'axios';

class Geo {
  async initGeo() {
    if (Platform.OS === 'android') {
      await PermissionsAndroid.request(
        PermissionsAndroid.PERMISSIONS.ACCESS_COARSE_LOCATION,
      );
    }
    await init({
      // yizhi-android的key
      ios: '188152eebd9362f742cff4ba4c5df57a',
      android: '188152eebd9362f742cff4ba4c5df57a',
    });
    return Promise.resolve();
  }

  async getCurrentPosition() {
    return new Promise((resolve, reject) => {
      console.log('开始定位');
      Geolocation.getCurrentPosition(({coords}) => {
        resolve(coords);
      }, reject);
    });
  }

  async getCityByLocation() {
    // 经纬度定位到城市
    const {longitude, latitude} = await this.getCurrentPosition();
    const res = await axios.get('https://restapi.amap.com/v3/geocode/regeo', {
      params: {
        location: `${longitude},${latitude}`,
        // yizhi-web的key
        key: '9bde218c1633a7c000747c584cc6c725',
      },
    });
    return Promise.resolve(res.data);
  }
}
export default new Geo();
