package cn.easyz.server.service.v1.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import cn.easyz.common.model.dto.*;
import cn.easyz.common.model.enums.SexEnum;
import cn.easyz.common.model.pojo.mongodb.RecommendUser;
import cn.easyz.common.model.pojo.mongodb.Users;
import cn.easyz.common.model.pojo.mysql.ApUser;
import cn.easyz.common.model.pojo.mysql.ApUserInfo;
import cn.easyz.common.model.request.RecommendUserRequest;
import cn.easyz.common.model.vo.ResponseResult;
import cn.easyz.common.util.UserThreadLocal;
import cn.easyz.dubbo.api.v1.UsersApi;
import cn.easyz.server.service.v1.ApUserInfoService;
import cn.easyz.server.service.v1.HuanXinService;
import cn.easyz.server.service.v1.PicUploadService;
import cn.easyz.server.service.v1.UsersService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Users service.
 */
@Service
public class UsersServiceImpl implements UsersService {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Autowired
    private PicUploadService picUploadService;
    @Autowired
    private HuanXinService huanXinService;
    @Autowired
    private ApUserInfoService apUserInfoService;
    @DubboReference(version = "1.0.0")
    private UsersApi usersApi;
    private final Long defaultRecommendUser = Long.valueOf("2");
    private final String defaultRecommendUsers = "2,3,4,5";
    @Override
    public ResponseResult user() {
        return this.huanXinService.user();
    }
    @Override
    public ResponseResult register(Long userId) {
        return this.huanXinService.register(userId);
    }
    @Override
    public ResponseResult sendMsg(String target, String msg, String type) {
        return this.huanXinService.sendMsg(target, msg, type);
    }
    @Override
    public ResponseResult saveUserInfo(Map<String, String> param) {
        return (this.apUserInfoService.saveUserInfo(param)) ? ResponseResult.ok() : ResponseResult.fail();
    }
    @Override
    public ResponseResult saveUserLogo(MultipartFile file) {
        return (this.apUserInfoService.saveUserLogo(file)) ? ResponseResult.ok() : ResponseResult.fail();
    }
    @Override
    public ResponseResult queryUserInfo(Long userId) {
        ApUserInfo apUserInfo = this.apUserInfoService.queryUserInfoByUserId(userId);
        UserInfoDTO dto = new UserInfoDTO();
        dto.setId(Long.valueOf(apUserInfo.getId()));
        dto.setLogo(apUserInfo.getLogo());
        dto.setNickName(apUserInfo.getNickName());
        dto.setBirthday(apUserInfo.getBirthday());
        dto.setAge(apUserInfo.getAge().toString());
        dto.setSex(apUserInfo.getSex().name().toLowerCase());
        dto.setCity(apUserInfo.getCity());
        dto.setEdu(apUserInfo.getEdu());
        dto.setIndustry(apUserInfo.getIndustry());
        return ResponseResult.ok(dto);
    }
    @Override
    public ResponseResult updateUserInfo(Map<String, String> param) {
        ApUser user = UserThreadLocal.get();
        ApUserInfo apserInfo = new ApUserInfo();
        apserInfo.setUserId(user.getId());
        apserInfo.setNickName(param.get("nickName"));
        apserInfo.setSex(StringUtils.equalsIgnoreCase(param.get("sex"), "man") ? SexEnum.MAN : SexEnum.WOMAN);
        apserInfo.setAge(Integer.valueOf(param.get("age")));
        apserInfo.setEdu(param.get("edu"));
        apserInfo.setCity(param.get("city"));
        apserInfo.setBirthday(param.get("birthday"));
        apserInfo.setIndustry(param.get("industry"));
        return (this.apUserInfoService.updateUserInfo(apserInfo)) ? ResponseResult.ok() : ResponseResult.fail();
    }
    @Override
    public ResponseResult addUsers(Long friendId) {
        ApUser user = UserThreadLocal.get();
        Users users = new Users();
        users.setUserId(Long.valueOf(user.getId()));
        users.setFriendId(friendId);
        // mongodb
        if (StringUtils.isNotEmpty(this.usersApi.saveUsers(users))) {
            return this.huanXinService.contactUsers(Long.valueOf(user.getId()), friendId);
        }
        return ResponseResult.fail();
    }
    @Override
    public ResponseResult queryUsersList(Integer page, Integer size, String keyword) {
        ApUser user = UserThreadLocal.get();
        List<UsersDTO> dtos = new ArrayList<>();
        if (StringUtils.isEmpty(keyword)) {
            List<Users> usersList = this.usersApi.queryUsersList(Long.valueOf(user.getId()), page, size);
            for (Users users : usersList) {
                // 填充UserInfo
                ApUserInfo userInfo = this.apUserInfoService.queryUserInfoByUserId(users.getFriendId());
                UsersDTO dto = new UsersDTO();
                dto.setUserId(String.valueOf(users.getFriendId()));
                dto.setLogo(userInfo.getLogo());
                dto.setNickName(userInfo.getNickName());
                dto.setSex(userInfo.getSex().name().toLowerCase());
                dto.setAge(Integer.valueOf(userInfo.getAge().toString()));
                dto.setCity(StringUtils.substringBefore(userInfo.getCity(), "-"));
                dtos.add(dto);
            }
        } else {
            List<Users> usersList = this.usersApi.queryUsersList(Long.valueOf(user.getId()));
            List<Long> userIds = usersList.stream().map(Users::getFriendId).collect(Collectors.toList());
            QueryWrapper<ApUserInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.like("nick_name", keyword).in("user_id", userIds);
            List<ApUserInfo> userInfoList = this.apUserInfoService.queryUserInfoList(queryWrapper);
            for (ApUserInfo userInfo : userInfoList) {
                UsersDTO dto = new UsersDTO();
                dto.setUserId(String.valueOf(userInfo.getUserId()));
                dto.setLogo(userInfo.getLogo());
                dto.setNickName(userInfo.getNickName());
                dto.setSex(userInfo.getSex().name().toLowerCase());
                dto.setAge(Integer.valueOf(userInfo.getAge().toString()));
                dto.setCity(StringUtils.substringBefore(userInfo.getCity(), "-"));
                dtos.add(dto);
            }
            // 未分页
            page = 0;
            size = 0;
        }
        return ResponseResult.ok(new PageInfoDTO<>(0, page, size, dtos));
    }
    @Override
    public ResponseResult queryFollowCounts() {
        ApUser user = UserThreadLocal.get();
        return ResponseResult.ok(this.usersApi.queryFollowCounts(Long.valueOf(user.getId())));
    }
    @Override
    public ResponseResult queryFollowList(String type, Integer page, Integer size) {
        // 查找关注列表FriendId
        ApUser user = UserThreadLocal.get();
        List<Long> userIds = new ArrayList<>();
        switch (type) {
            case "mutualFollow":
                this.usersApi.queryMutualFollowList(Long.valueOf(user.getId()), page, size)
                        .forEach(users -> userIds.add(users.getFriendId()));
                break;
            case "follow":
                this.usersApi.queryFollowList(Long.valueOf(user.getId()), page, size)
                        .forEach(users -> userIds.add(users.getFriendId()));
                break;
            case "fans":
                this.usersApi.queryFansList(Long.valueOf(user.getId()), page, size)
                        .forEach(users -> userIds.add(users.getUserId()));
                break;
            default:
                break;
        }
        // 填充UserInfo
        List<FollowUserDTO> dtos = new ArrayList<>();
        QueryWrapper<ApUserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("user_id", userIds);
        List<ApUserInfo> userInfoList = this.apUserInfoService.queryUserInfoList(queryWrapper);
        for (ApUserInfo userInfo : userInfoList) {
            FollowUserDTO dto = new FollowUserDTO();
            dto.setId(Long.valueOf(userInfo.getUserId()));
            dto.setLogo(userInfo.getLogo());
            dto.setNickName(userInfo.getNickName());
            dto.setSex(userInfo.getSex().name().toLowerCase());
            dto.setAge(userInfo.getAge());
            dto.setCity(userInfo.getCity());
            dto.setEdu(userInfo.getEdu());
            dto.setSimilarity((double) Math.round(this.usersApi.querySimilarity(Long.valueOf(user.getId()),
                    Long.valueOf(userInfo.getUserId()))));
            dtos.add(dto);
        }
        return ResponseResult.ok(new PageInfoDTO<>(0, page, size, dtos));
    }
    @Override
    public ResponseResult follow(Long friendId) {
        ApUser user = UserThreadLocal.get();
        if (this.usersApi.followUser(Long.valueOf(user.getId()),
                friendId) != null) {
            if (this.usersApi.isMutualFollow(Long.valueOf(user.getId()), friendId)) {
                this.addUsers(friendId);
            }
            return ResponseResult.ok();
        }
        return ResponseResult.fail();
    }
    @Override
    public ResponseResult unFollow(Long friendId) {
        ApUser user = UserThreadLocal.get();
        return (this.usersApi.deleteFollowUser(Long.valueOf(user.getId()),
                friendId) != null) ? ResponseResult.ok() : ResponseResult.fail();
    }
    @Override
    public ResponseResult queryTodayBest() {
        // 校验token是否有效,通过user的接口进行校验
        ApUser user = UserThreadLocal.get();
        RecommendUserDTO dto = new RecommendUserDTO();
        // 通过用户id寻找最佳推荐用户
        RecommendUser recommendUser = this.usersApi.queryWithMaxSimilarity(Long.valueOf(user.getId()));
        if (null == recommendUser) {
            // 给出默认的推荐用户 在配置文件中有设置默认用户直接注入
            // 获取推荐用户个人信息
            ApUserInfo userInfo = this.apUserInfoService.queryUserInfoByUserId(defaultRecommendUser);
            // 补全个人信息
            dto.setFriendId(defaultRecommendUser);
            dto.setLogo(userInfo.getLogo());
            dto.setNickName(userInfo.getNickName());
            dto.setSex(userInfo.getSex().getValue() == 1 ? "man" : "woman");
            dto.setAge(userInfo.getAge());
            dto.setTags(StringUtils.split(userInfo.getTags(), ","));
            dto.setSimilarity(Double.valueOf(49));
        } else {
            // 获取推荐用户个人信息
            ApUserInfo userInfo = this.apUserInfoService.queryUserInfoByUserId(recommendUser.getFriendId());
            // 补全个人信息
            dto.setFriendId(recommendUser.getFriendId());
            dto.setLogo(userInfo.getLogo());
            dto.setNickName(userInfo.getNickName());
            dto.setSex(userInfo.getSex().getValue() == 1 ? "man" : "woman");
            dto.setAge(userInfo.getAge());
            dto.setTags(StringUtils.split(userInfo.getTags(), ","));
            // 获得相似度 取整
            dto.setSimilarity(Double.valueOf(Math.round(recommendUser.getSimilarity())));
        }
        return ResponseResult.ok(dto);
    }
    @Override
    public ResponseResult queryRecommendUser(Long friendId) {
        ApUser user = UserThreadLocal.get();
        ApUserInfo userInfo = this.apUserInfoService.queryUserInfoByUserId(friendId);
        RecommendUserDTO dto = new RecommendUserDTO();
        dto.setFriendId(friendId);
        dto.setLogo(userInfo.getLogo());
        dto.setNickName(userInfo.getNickName());
        dto.setSex(userInfo.getSex().name().toLowerCase(Locale.ROOT));
        dto.setAge(userInfo.getAge());
        dto.setTags(StringUtils.split(userInfo.getTags(), ","));
        double similarity = this.usersApi.querySimilarity(friendId, Long.valueOf(user.getId()));
        if (similarity == 0) {
            similarity = 90;
        }
        dto.setSimilarity(Double.valueOf(Math.round(similarity)));
        return ResponseResult.ok(dto);
    }
    @Override
    public ResponseResult queryRecommendUserList(RecommendUserRequest request) {
        //获得用户对象
        ApUser user = UserThreadLocal.get();
        PageInfoDTO dto = this.usersApi.queryRecommendUserList(Long.valueOf(user.getId()),
                request.getPage(),
                request.getSize());
        List<RecommendUser> records = dto.getRecords();
        //判断集合中是否有数据
        if (CollectionUtils.isEmpty(records)) {
            //进行切割
            String[] friendIdsStr = StringUtils.split(defaultRecommendUsers, ",");
            for (String s : friendIdsStr) {
                RecommendUser recommendUser = new RecommendUser();
                recommendUser.setFriendId(Long.valueOf(s));
                recommendUser.setId(ObjectId.get());
                recommendUser.setSimilarity(RandomUtils.nextDouble(70d, 90d));
                records.add(recommendUser);
            }
        }
        //收集推荐用户的id
        Set<Long> friendIds = new HashSet<>();
        for (RecommendUser record : records) {
            friendIds.add(record.getFriendId());
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        //用户的id参数
        queryWrapper.in("user_id", friendIds);

        /*//查询城市参数
        if (StringUtils.isNotEmpty(param.getCity())) {
            queryWrapper.like("city", param.getCity());
        }

        //查询性别参数
        if (StringUtils.isNotEmpty(param.getSex())) {
            queryWrapper.eq("sex", StringUtils.equals(param.getSex(), "man") ? 1 : 2);
        }

        //查询年龄参数
        if (param.getAge() != null) {
            queryWrapper.le("age", param.getAge());
        }*/
        //
        List<ApUserInfo> userInfoList = this.apUserInfoService.queryUserInfoList(queryWrapper);
        if (CollectionUtils.isEmpty(userInfoList)) {
            //没有查到用户基本信息
            return ResponseResult.fail(dto);
        }
        List<RecommendUserDTO> dtos = new ArrayList<>();
        for (ApUserInfo userInfo : userInfoList) {
            RecommendUserDTO dto2 = new RecommendUserDTO();
            //补全个人信息
            dto2.setFriendId(Long.valueOf(userInfo.getUserId()));
            dto2.setLogo(userInfo.getLogo());
            dto2.setAge(userInfo.getAge());
            dto2.setNickName(userInfo.getNickName());
            dto2.setSex(userInfo.getSex().getValue() == 1 ? "man" : "woman");
            dto2.setTags(StringUtils.split(userInfo.getTags(), ","));
            //设置相似度
            for (RecommendUser record : records) {
                if (record.getFriendId() == userInfo.getUserId().longValue()) {
                    double similarity = Math.round(record.getSimilarity());
                    dto2.setSimilarity(similarity);
                    break;
                }
            }
            dtos.add(dto2);
        }
        //相似度倒序排列
        Collections.sort(dtos, (o1, o2) -> new Long((long) (o2.getSimilarity() - o1.getSimilarity())).intValue());
        dto.setRecords(dtos);
        return ResponseResult.ok(dto);
    }
    @Override
    public ResponseResult queryNearUser(String sex, String distance) {
        ApUser user = UserThreadLocal.get();
        // 我的位置
        UserLocationDTO dto = this.usersApi.queryLocationByUserId(Long.valueOf(user.getId()));
        Double longitude = dto.getLongitude();
        Double latitude = dto.getLatitude();
        // 查询附近的用户
        List<UserLocationDTO> dtos = this.usersApi.queryUserFromLocation(longitude, latitude,
                Integer.valueOf(distance));
        List<Long> userIds = dtos.stream().map(UserLocationDTO::getUserId).collect(Collectors.toList());
        // 查询用户信息
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("user_id", userIds);
        if (StringUtils.equalsIgnoreCase(sex, "man")) {
            queryWrapper.eq("sex", SexEnum.MAN);
        }
        if (StringUtils.equalsIgnoreCase(sex, "woman")) {
            queryWrapper.eq("sex", SexEnum.WOMAN);
        }
        List<ApUserInfo> userInfoList = this.apUserInfoService.queryUserInfoList(queryWrapper);
        // 填充数据
        List<NearUserDTO> dtos1 = new ArrayList<>();
        for (ApUserInfo userInfo : userInfoList) {
            if (userInfo.getUserId().equals(user.getId())) {
                continue;
            }
            NearUserDTO dto1 = new NearUserDTO();
            dto1.setUserId(Long.valueOf(userInfo.getUserId()));
            dto1.setLogo(userInfo.getLogo());
            dto1.setNickName(userInfo.getNickName());
            dtos1.add(dto1);
        }
        return ResponseResult.ok(dtos1);
    }
    @Override
    public ResponseResult updateLocation(Map<String, Object> param) {
        Double longitude = Double.valueOf(param.get("longitude").toString());
        Double latitude = Double.valueOf(param.get("latitude").toString());
        String address = param.get("address").toString();
        Boolean aBoolean = this.usersApi.updateLocation(Long.valueOf(UserThreadLocal.get().getId()),
                longitude,
                latitude,
                address);
        return (aBoolean) ? ResponseResult.ok() : ResponseResult.fail();
    }
}
