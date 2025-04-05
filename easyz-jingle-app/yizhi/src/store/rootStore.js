import {action, observable} from 'mobx';
// 全局变量
class RootStore {
  @observable mobile = '';
  @observable token = '';
  @observable userId = '';
  @action setUserInfo(mobile, token, userId) {
    this.mobile = mobile;
    this.token = token;
    this.userId = userId;
  }
  @action clearUserInfo() {
    this.mobile = '';
    this.token = '';
    this.userId = '';
  }
}
export default new RootStore();
