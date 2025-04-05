// export const BASE_URL = 'http://192.168.3.3:7000';
export const BASE_URL = 'http://127.0.0.1:17000';
// login
export const LOGIN_SEND = 'yizhi-login/api/v1/login/send';
export const LOGIN_LOGIN = 'yizhi-login/api/v1/login/login';
export const LOGIN_SAVE_USER_INFO = 'yizhi-login/api/v1/login/saveUserInfo';
export const LOGIN_SAVE_USER_LOGO = 'yizhi-login/api/v1/login/saveUserLogo';
export const LOGIN_MOBILE_SEND = 'yizhi-login/api/v1/login/mobile/send';
export const LOGIN_MOBILE_NEW = 'yizhi-login/api/v1/login/mobile/new';
// users
export const USERS_UPDATE_LOCATION = 'yizhi-server/api/v1/users/updateLocation';
export const USERS_NEAR = 'yizhi-server/api/v1/users/near';
export const USERS_TODAYBEST = 'yizhi-server/api/v1/users/todayBest';
export const USERS_ID = 'yizhi-server/api/v1/users/:userId';
export const USERS_RECOMMEND_USER_LIST =
  'yizhi-server/api/v1/users/recommendUserList';
export const USERS_SAVE_USER_LOGO = 'yizhi-server/api/v1/users/saveUserLogo';
export const USERS_HUANXIN = 'yizhi-server/api/v1/users/huanxin';
export const USERS_LIST = 'yizhi-server/api/v1/users/list';
export const USERS_ADD_FRIENDID = 'yizhi-server/api/v1/users/add/:friendId';
export const USERS_FOLLOW_FRIENDID =
  'yizhi-server/api/v1/users/follow/:friendId';
export const USERS_UNFOLLOW_FRIENDID =
  'yizhi-server/api/v1/users/unfollow/:friendId';
export const USERS_QUERY_USER_INFO =
  'yizhi-server/api/v1/users/queryUserInfo/:userId';
export const USERS_FOLLOW_COUNTS = 'yizhi-server/api/v1/users/follow/counts';
export const USERS_FOLLOW_LIST_TYPE =
  'yizhi-server/api/v1/users/follow/list/:type';
export const USERS_UPDATE_USER_INFO =
  'yizhi-server/api/v1/users/updateUserInfo';
// post
export const POST_RECOMMEND = 'yizhi-server/api/v1/post/recommend';
export const POST_PUBLISH = 'yizhi-server/api/v1/post/publish';
export const POST_USER = 'yizhi-server/api/v1/post/user/:userId';
export const POST_FRIEND = 'yizhi-server/api/v1/post/friend';
export const POST_ID = 'yizhi-server/api/v1/post/:id';
export const POST_ID_TEXT = 'yizhi-server/api/v1/comment/post/:id/text';
export const POST_ID_LIKE = 'yizhi-server/api/v1/comment/post/:id/like';
export const POST_ID_DISLIKE = 'yizhi-server/api/v1/comment/post/:id/dislike';
export const POST_ID_LOVE = 'yizhi-server/api/v1/comment/post/:id/love';
export const POST_ID_UNLOVE = 'yizhi-server/api/v1/comment/post/:id/unlove';
// video
export const VIDEO_RECOMMEND = 'yizhi-server/api/v1/video/recommend';
export const VIDEO_PUBLISH = 'yizhi-server/api/v1/video/publish';
export const VIDEO_USER = 'yizhi-server/api/v1/video/user/:userId';
export const VIDEO_FRIEND = 'yizhi-server/api/v1/video/friend';
export const VIDEO_ID = 'yizhi-server/api/v1/video/:id';
export const VIDEO_ID_TEXT = 'yizhi-server/api/v1/comment/video/:id/text';
export const VIDEO_ID_LIKE = 'yizhi-server/api/v1/comment/video/:id/like';
export const VIDEO_ID_DISLIKE = 'yizhi-server/api/v1/comment/video/:id/dislike';
export const VIDEO_ID_LOVE = 'yizhi-server/api/v1/comment/video/:id/love';
export const VIDEO_ID_UNLOVE = 'yizhi-server/api/v1/comment/video/:id/unlove';
// comment
export const COMMENT_PUBLISH_ID = 'yizhi-server/api/v1/comment/:publishId';
export const COMMENT_ID_LIKE = 'yizhi-server/api/v1/comment/comment/:id/like';
export const COMMENT_ID_DISLIKE =
  'yizhi-server/api/v1/comment/comment/:id/dislike';
// message
export const MESSAGE_COMMENT_TYPE = 'yizhi-server/api/v1/message/:commentType';
export const MESSAGE_ANNOUNCEMENT = 'yizhi-server/api/v1/message/announcement';
// article
export const ARTICLE_SAVE = 'yizhi-server/api/v1/article/save';
export const ARTICLE_TYPE = 'yizhi-server/api/v1/article/list/:articleType';
export const ARTICLE_RECOMMEND_V1 = 'yizhi-server/api/v1/article/recommend';
export const ARTICLE_RECOMMEND_V2 = 'yizhi-server/api/v2/article/recommend';
export const ARTICLE_RID = 'yizhi-server/api/v1/article/:articleRid';
export const ARTICLE_ID_LIKE = 'yizhi-server/api/v1/comment/article/:id/like';
export const ARTICLE_ID_DISLIKE =
  'yizhi-server/api/v1/comment/article/:id/dislike';
// material
export const MATERIAL_SAVE = 'yizhi-server/api/v1/material/save';
export const MATERIAL_TYPE = 'yizhi-server/api/v1/material/list/:materialType';
export const MATERIAL_RECOMMEND_V1 = 'yizhi-server/api/v1/material/recommend';
export const MATERIAL_RECOMMEND_V2 = 'yizhi-server/api/v2/material/recommend';
export const MATERIAL_RID = 'yizhi-server/api/v1/material/:materialRid';
export const MATERIAL_ID_LOVE = 'yizhi-server/api/v1/comment/material/:id/love';
export const MATERIAL_ID_UNLOVE =
  'yizhi-server/api/v1/comment/material/:id/unlove';
