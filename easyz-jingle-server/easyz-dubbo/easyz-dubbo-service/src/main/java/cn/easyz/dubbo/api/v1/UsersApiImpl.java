package cn.easyz.dubbo.api.v1;

import cn.easyz.common.model.dto.FollowCountDTO;
import cn.easyz.common.model.dto.PageInfoDTO;
import cn.easyz.common.model.dto.UserLocationDTO;
import cn.easyz.common.model.pojo.mongodb.FollowUser;
import cn.easyz.common.model.pojo.mongodb.RecommendUser;
import cn.easyz.common.model.pojo.mongodb.UserLocation;
import cn.easyz.common.model.pojo.mongodb.Users;
import org.apache.dubbo.config.annotation.DubboService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.text.DecimalFormat;
import java.util.List;

/**
 * The type Users api.
 */
@DubboService(version = "1.0.0")
public class UsersApiImpl implements UsersApi {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public String saveUsers(Users users) {
        //判断好友关系是否存在
        Query query = Query.query(Criteria.where("userId")
                .is(users.getUserId())
                .and("friendId")
                .is(users.getFriendId()));
        if (this.mongoTemplate.count(query, Users.class) == 0) {
            users.setId(ObjectId.get());
            users.setCreated(System.currentTimeMillis());
            //不存在则建立好友关系
            this.mongoTemplate.save(users);
            return users.getId().toHexString();
        }
        return null;
    }
    @Override
    public List<Users> queryUsersList(Long userId) {
        Query query = Query.query(Criteria.where("userId").is(userId));
        return this.mongoTemplate.find(query, Users.class);
    }
    @Override
    public List<Users> queryUsersList(Long userId, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Order.desc("created")));
        Query query = Query.query(Criteria.where("userId").is(userId)).with(pageRequest);
        return this.mongoTemplate.find(query, Users.class);
    }
    @Override
    public String followUser(Long userId, Long friendId) {
        Query query = Query.query(Criteria.where("userId").is(userId).and("friendId").is(friendId));
        if (this.mongoTemplate.count(query, FollowUser.class) == 0 && this.mongoTemplate.count(query,
                Users.class) == 0) {
            FollowUser followUser = new FollowUser();
            followUser.setId(ObjectId.get());
            followUser.setUserId(userId);
            followUser.setFriendId(friendId);
            followUser.setCreated(System.currentTimeMillis());
            return this.mongoTemplate.save(followUser).getId().toHexString();
        }
        return null;
    }
    @Override
    public Boolean isMutualFollow(Long userId, Long friendId) {
        Criteria criteria = Criteria.where("userId").is(userId).and("friendId").is(friendId);
        Criteria criteria1 = Criteria.where("userId").is(friendId).and("friendId").is(userId);
        Criteria criteria2 = new Criteria().orOperator(criteria, criteria1);
        return this.mongoTemplate.count(Query.query(criteria2), FollowUser.class) > 1;
    }
    @Override
    public Boolean deleteFollowUser(Long userId, Long friendId) {
        Query query = Query.query(Criteria.where("userId").is(userId).and("friendId").is(friendId));
        if (this.mongoTemplate.count(query, Users.class) != 0) {
            this.mongoTemplate.remove(query, Users.class);
        }
        return this.mongoTemplate.remove(query, FollowUser.class).getDeletedCount() > 0;
    }
    @Override
    public List<FollowUser> queryMutualFollowList(Long userId, Integer page, Integer size) {
        List<FollowUser> followUsers = this.mongoTemplate.find(Query.query(Criteria.where("userId").is(userId)),
                FollowUser.class);
        List<Long> friendIds = followUsers.stream()
                .map(FollowUser::getFriendId)
                .collect(java.util.stream.Collectors.toList());
        Query query = Query.query(Criteria.where("userId").in(friendIds).and("friendId").is(userId));
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Order.desc("created")));
        query.with(pageRequest);
        return this.mongoTemplate.find(query, FollowUser.class);
    }
    @Override
    public List<FollowUser> queryFollowList(Long userId, Integer page, Integer size) {
        Query query = Query.query(Criteria.where("userId").is(userId));
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Order.desc("created")));
        query.with(pageRequest);
        return this.mongoTemplate.find(query, FollowUser.class);
    }
    @Override
    public List<FollowUser> queryFansList(Long userId, Integer page, Integer size) {
        Query query = Query.query(Criteria.where("friendId").is(userId));
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Order.desc("created")));
        query.with(pageRequest);
        return this.mongoTemplate.find(query, FollowUser.class);
    }
    @Override
    public FollowCountDTO queryFollowCounts(Long userId) {
        FollowCountDTO followCountDTO = new FollowCountDTO();
        followCountDTO.setMutualFollowCount(this.queryMutualFollowCount(userId));
        followCountDTO.setFollowCount(this.queryFollowCount(userId));
        followCountDTO.setFanCount(this.queryFansCount(userId));
        return followCountDTO;
    }
    private Long queryMutualFollowCount(Long userId) {
        List<FollowUser> followUsers = this.mongoTemplate.find(Query.query(Criteria.where("userId").is(userId)),
                FollowUser.class);
        List<Long> friendIds = followUsers.stream()
                .map(FollowUser::getFriendId)
                .collect(java.util.stream.Collectors.toList());
        return this.mongoTemplate.count(Query.query(Criteria.where("userId").in(friendIds).and("friendId").is(userId)),
                FollowUser.class);
    }
    private Long queryFollowCount(Long userId) {
        return this.mongoTemplate.count(Query.query(Criteria.where("userId").is(userId)), FollowUser.class);
    }
    private Long queryFansCount(Long userId) {
        return this.mongoTemplate.count(Query.query(Criteria.where("friendId").is(userId)), FollowUser.class);
    }
    @Override
    public RecommendUser queryWithMaxSimilarity(Long userId) {
        Query query = Query.query(Criteria.where("userId").is(userId))
                .with(Sort.by(Sort.Order.desc("similarity")))
                .limit(1);
        return mongoTemplate.findOne(query, RecommendUser.class);
    }
    @Override
    public PageInfoDTO<RecommendUser> queryRecommendUserList(Long userId, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Order.desc("similarity")));
        Query query = Query.query(Criteria.where("userId").is(userId)).with(pageRequest);
        List<RecommendUser> recommendUserList = mongoTemplate.find(query, RecommendUser.class);
        return new PageInfoDTO<>(0, page, size, recommendUserList);
    }
    @Override
    public Double querySimilarity(Long friendId, Long userId) {
        Query query = Query.query(Criteria
                .where("userId").is(userId)
                .and("friendId").is(friendId));
        RecommendUser recommendUser = this.mongoTemplate.findOne(query, RecommendUser.class);
        if (null == recommendUser) {
            return Double.valueOf(0);
        }
        return recommendUser.getSimilarity();
    }
    @Override
    public Boolean updateLocation(Long userId, Double longitude, Double latitude, String address) {
        Query query = Query.query(Criteria.where("userId").is(userId));
        UserLocation userLocation = this.mongoTemplate.findOne(query, UserLocation.class);
        if (userLocation != null) {
            Update update = Update.update("location", new GeoJsonPoint(longitude, latitude))
                    .set("address", address)
                    .set("updated", System.currentTimeMillis())
                    .set("lastUpdated", userLocation.getUpdated());
            return this.mongoTemplate.updateFirst(query,
                    update,
                    UserLocation.class).getModifiedCount() > 0;
        } else {
            userLocation = new UserLocation();
            userLocation.setId(ObjectId.get());
            userLocation.setUserId(userId);
            userLocation.setLocation(new GeoJsonPoint(longitude, latitude));
            userLocation.setAddress(address);
            userLocation.setCreated(System.currentTimeMillis());
            userLocation.setUpdated(System.currentTimeMillis());
            userLocation.setLastUpdated(System.currentTimeMillis());
            return this.mongoTemplate.save(userLocation).getId() != null;
        }
    }
    @Override
    public UserLocationDTO queryLocationByUserId(Long userId) {
        Query query = Query.query(Criteria.where("userId").is(userId));
        return UserLocationDTO.format(this.mongoTemplate.findOne(query, UserLocation.class));
    }
    @Override
    public List<UserLocationDTO> queryUserFromLocation(Double longitude, Double latitude, Integer range) {
        GeoJsonPoint point = new GeoJsonPoint(longitude, latitude);
        // 以米为单位
        Distance distance = new Distance(range * 0.001, Metrics.KILOMETERS);
        Circle circle = new Circle(point, distance);
        return UserLocationDTO.formatToList(this.mongoTemplate.find(Query.query(Criteria.where("location")
                .withinSphere(circle)), UserLocation.class));
    }
    @Override
    public String queryDistance(Long userId, String longitude, String latitude) {
        GeoJsonPoint point = new GeoJsonPoint(Double.valueOf(longitude), Double.valueOf(latitude));
        NearQuery nearQuery = NearQuery.near((point), Metrics.KILOMETERS)
                .query(Query.query(Criteria.where("userId").is(userId)));
        GeoResults<UserLocation> geoResults = this.mongoTemplate.query(UserLocation.class)
                .near(nearQuery).all();
        return new DecimalFormat("#.##").format(geoResults.getAverageDistance().getValue()) + "";
    }
}
