package cn.easyz.neo4j.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.easyz.common.model.mapper.ApUserInfoMapper;
import cn.easyz.common.model.pojo.mongodb.RecommendUser;
import cn.easyz.common.model.pojo.mysql.ApUserInfo;
import cn.easyz.common.model.pojo.neo4j.node.UserNode;
import cn.easyz.common.model.pojo.neo4j.relationship.UserRecommendRelationship;
import cn.easyz.common.model.repository.UserRecommendRelationshipRepository;
import cn.easyz.common.model.repository.UserRepository;
import cn.easyz.common.model.vo.ResponseResult;
import cn.easyz.neo4j.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * The type User service.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRecommendRelationshipRepository userRecommendRelationshipRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Resource
    private ApUserInfoMapper apUserInfoMapper;
    @Override
    public ResponseResult saveUser() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("status", 1);
        List<ApUserInfo> apUserInfoList = this.apUserInfoMapper.selectList(queryWrapper);
        for (ApUserInfo apUserInfo : apUserInfoList) {
            UserNode userNode = UserNode.builder()
                    .userId(apUserInfo.getUserId())
                    .nickName(apUserInfo.getNickName())
                    .logo(apUserInfo.getLogo())
                    .tags(apUserInfo.getTags())
                    .sex(apUserInfo.getSex().getValue())
                    .age(apUserInfo.getAge())
                    .edu(apUserInfo.getEdu())
                    .city(apUserInfo.getCity())
                    .birthday(apUserInfo.getBirthday())
                    .coverPic(apUserInfo.getCoverPic())
                    .industry(apUserInfo.getIndustry())
                    .build();
            this.userRepository.save(userNode);
        }
        return ResponseResult.ok();
    }
    @Override
    public ResponseResult saveUserRecommendRelationship() {
        Iterable<UserNode> userNodes = userRepository.findAll();
        for (UserNode userNode : userNodes) {
            Query query = Query.query(Criteria.where("userId").is(userNode.getUserId()).and("similarity").gt(70))
                    .with(Sort.by(Sort.Order.desc("similarity")))
                    .limit(3);
            List<RecommendUser> recommendUserList = mongoTemplate.find(query, RecommendUser.class);
            for (RecommendUser recommendUser : recommendUserList) {
                UserRecommendRelationship userRecommendRelationship = UserRecommendRelationship.builder()
                        .start(userRepository.findByUserId(Math.toIntExact(recommendUser.getFriendId())))
                        .end(userNode)
                        .similarity(recommendUser.getSimilarity()).
                        description("相似")
                        .build();
                userRecommendRelationshipRepository.save(userRecommendRelationship);
            }
        }
        return ResponseResult.ok();
    }
}
