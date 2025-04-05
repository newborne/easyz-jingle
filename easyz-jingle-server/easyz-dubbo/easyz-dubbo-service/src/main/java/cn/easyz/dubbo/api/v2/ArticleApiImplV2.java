package cn.easyz.dubbo.api.v2;

import cn.easyz.common.client.Neo4jFeignClient;
import cn.easyz.common.model.pojo.mongodb.Article;
import cn.easyz.common.model.pojo.neo4j.node.ArticleNode;
import cn.easyz.dubbo.api.v1.ArticleApi;
import org.apache.dubbo.config.annotation.DubboService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Article api impl v 2.
 */
@DubboService(version = "2.0.0")
public class ArticleApiImplV2 implements ArticleApi {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private Neo4jFeignClient neo4jFeignClient;
    @Override
    public String saveArticle(Article article) {
        return null;
    }
    @Override
    public List<Article> queryArticleList(Integer articleType, Integer page, Integer size) {
        return null;
    }
    @Override
    public List<Article> queryRecommendArticleList(Long userId, Integer page, Integer size) {
        List<Article> articleList = new ArrayList<>();
        List<ArticleNode> articleNodeList = this.neo4jFeignClient.queryRecommendArticleList(Math.toIntExact(userId),
                page,
                size);
        for (ArticleNode articleNode : articleNodeList) {
            Article article = new Article();
            article.setId(ObjectId.get());
            article.setArticleRid(articleNode.getArticleRid());
            article.setTitle(articleNode.getTitle());
            article.setArticleType(articleNode.getArticleType());
            article.setTags(articleNode.getTags());
            article.setText(articleNode.getText());
            articleList.add(article);
        }
        return articleList;
    }
    @Override
    public Article queryArticleByArticleRid(String articleRid) {
        Query query = Query.query(Criteria.where("articleRid").is(Long.valueOf(articleRid)));
        return this.mongoTemplate.findOne(query, Article.class);
    }
    @Override
    public Article queryArticleById(String id) {
        return this.mongoTemplate.findById(id, Article.class);
    }


}
