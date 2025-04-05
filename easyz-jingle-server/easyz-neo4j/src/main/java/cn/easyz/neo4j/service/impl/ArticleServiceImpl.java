package cn.easyz.neo4j.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.easyz.common.model.mapper.ApDictionaryMapper;
import cn.easyz.common.model.pojo.mongodb.Article;
import cn.easyz.common.model.pojo.mysql.ApDictionary;
import cn.easyz.common.model.pojo.neo4j.node.ArticleNode;
import cn.easyz.common.model.pojo.neo4j.node.ArticleTypeNode;
import cn.easyz.common.model.pojo.neo4j.node.UserNode;
import cn.easyz.common.model.pojo.neo4j.relationship.ArticleBelongRelationship;
import cn.easyz.common.model.pojo.neo4j.relationship.ArticleRecommendRelationship;
import cn.easyz.common.model.repository.*;
import cn.easyz.common.model.vo.ResponseResult;
import cn.easyz.neo4j.service.ArticleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Article service.
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ArticleTypeRepository articleTypeRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleBelongRelationshipRepository articleBelongRelationshipRepository;
    @Autowired
    private ArticleRecommendRelationshipRepository articleRecommendRelationshipRepository;
    @Resource
    private ApDictionaryMapper apDictionaryMapper;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Override
    public ResponseResult saveArticleType() {
        List<ApDictionary> articleTypeList = this.apDictionaryMapper.selectList(new QueryWrapper<ApDictionary>().eq(
                "parent_id",
                10000));
        for (ApDictionary articleType : articleTypeList) {
            ArticleTypeNode articleTypeNode = ArticleTypeNode.builder()
                    .articleType(articleType.getValue())
                    .articleTypeName(articleType.getName())
                    .build();
            this.articleTypeRepository.save(articleTypeNode);
        }
        return ResponseResult.ok();
    }
    @Override
    public ResponseResult saveArticle() {
        this.articleTypeRepository.findAll().forEach(articleTypeNode -> {
            Query query = Query.query(Criteria.where("articleType").is(articleTypeNode.getArticleType()));
            List<Article> articleList = this.mongoTemplate.find(query, Article.class);
            System.out.println("类" + this.getClass().getName() + "中" + Thread.currentThread()
                    .getStackTrace()[1].getMethodName() + "方法：" + articleList);
            for (Article article : articleList) {
                ArticleNode articleNode = ArticleNode.builder()
                        .articleRid(article.getArticleRid())
                        .title(article.getTitle())
                        .articleType(article.getArticleType())
                        .tags(article.getTags())
                        .text(article.getText()).build();
                this.articleRepository.save(articleNode);
                ArticleBelongRelationship articleBelongRelationship = ArticleBelongRelationship.builder()
                        .start(articleNode)
                        .end(articleTypeNode)
                        .description("属于").build();
                this.articleBelongRelationshipRepository.save(articleBelongRelationship);
            }
        });
        return ResponseResult.ok();
    }
    @Override
    public ResponseResult saveArticleRecommendRelationship() {
        Iterable<UserNode> userNodes = userRepository.findAll();
        for (UserNode userNode : userNodes) {
            String key = "RECOMMEND_ARTICLE_" + userNode.getUserId();
            String value = this.redisTemplate.opsForValue().get(key);
            if (StringUtils.isNotEmpty(value)) {
                //命中了数据
                String[] articleRids = StringUtils.split(value, ",");
                List<Long> articleRidList = new ArrayList<>();
                for (int i = 0; i < articleRids.length; i++) {
                    articleRidList.add(Long.valueOf(articleRids[i]));
                }
                for (Long articleRid : articleRidList) {
                    ArticleRecommendRelationship articleRecommendRelationship = ArticleRecommendRelationship.builder()
                            .start(this.articleRepository.findByArticleRid(articleRid))
                            .end(userNode)
                            .description("推荐").build();
                    this.articleRecommendRelationshipRepository.save(articleRecommendRelationship);
                }
            }
        }
        return ResponseResult.ok();
    }
    @Override
    public List<ArticleNode> queryRecommendArticleList(Integer userId, Integer page, Integer size) {
        UserNode userNode = this.userRepository.findByUserId(userId);
        String key = "RECOMMEND_ARTICLE_" + userNode.getUserId();
        String value = this.redisTemplate.opsForValue().get(key);
        List<ArticleNode> articleNodeList = new ArrayList<>();
        if (StringUtils.isNotEmpty(value)) {
            String[] articleRids = StringUtils.split(value, ",");
            int startIndex = (page - 1) * size;
            if (startIndex < articleRids.length) {
                int endIndex = startIndex + size - 1;
                if (endIndex >= articleRids.length) {
                    endIndex = articleRids.length - 1;
                }
                List<Long> articleRidList = new ArrayList<>();
                for (int i = startIndex; i <= endIndex; i++) {
                    articleRidList.add(Long.valueOf(articleRids[i]));
                }
                for (Long articleRid : articleRidList) {
                    articleNodeList.add(this.articleRepository.findByArticleRid(articleRid));
                }
            }
        }
        System.out.println("类" + this.getClass().getName() + "中" + Thread.currentThread()
                .getStackTrace()[1].getMethodName() + "方法：" + "通过Neo4j查询推荐文章列表" + articleNodeList);
        return articleNodeList;
    }
}
