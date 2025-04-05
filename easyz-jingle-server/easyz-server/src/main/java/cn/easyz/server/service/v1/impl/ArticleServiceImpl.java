package cn.easyz.server.service.v1.impl;

import cn.easyz.common.model.dto.ArticleDTO;
import cn.easyz.common.model.dto.PageInfoDTO;
import cn.easyz.common.model.pojo.mongodb.Article;
import cn.easyz.common.model.pojo.mysql.ApUser;
import cn.easyz.common.model.vo.ResponseResult;
import cn.easyz.common.util.UserThreadLocal;
import cn.easyz.dubbo.api.v1.ArticleApi;
import cn.easyz.server.service.v1.ArticleService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Article service.
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @DubboReference(version = "1.0.0")
    private ArticleApi articleApi;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public ResponseResult queryArticleList(Integer articleType, Integer page, Integer size) {
        List<Article> articleList = this.articleApi.queryArticleList(articleType, page, size);
        List<ArticleDTO> dtos = new ArrayList<>();
        for (Article article : articleList) {
            dtos.add(this.fillValueToArticle(article));
        }
        return ResponseResult.ok(new PageInfoDTO<>(0,
                page,
                size, dtos));
    }

    @Override
    public ResponseResult saveArticle(String text, String title, String[] tags, Integer articleType) {
        Article article = new Article();
        article.setText(text);
        article.setTitle(title);
        article.setTags(tags);
        article.setArticleType(articleType);
        return (this.articleApi.saveArticle(article) != null) ? ResponseResult.ok() : ResponseResult.fail();
    }



    @Override
    public ResponseResult queryRecommendArticleList(Integer page, Integer size) {
        List<Article> articleList = this.articleApi.queryRecommendArticleList(
                Long.valueOf(UserThreadLocal.get().getId()), page, size);
        List<ArticleDTO> dtos = new ArrayList<>();
        for (Article article : articleList) {
            dtos.add(this.fillValueToArticle(article));
        }
        return ResponseResult.ok(new PageInfoDTO<>(0, page, size, dtos));
    }

    @Override
    public ResponseResult queryArticleByArticleRid(String articleRid) {
        return ResponseResult.ok(this.fillValueToArticle(this.articleApi.queryArticleByArticleRid(articleRid)));
    }

    private ArticleDTO fillValueToArticle(Article article) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(String.valueOf(article.getId()));
        dto.setArticleRid(String.valueOf(article.getArticleRid()));
        dto.setTitle(article.getTitle());
        dto.setTags(article.getTags());
        dto.setText(article.getText());
        // 填充评论相关
        ApUser user = UserThreadLocal.get();
        // 是否点赞
        String likeUserCommentKey = "COMMENT_LIKE_USER_" + user.getId() + "_" + dto.getId();
        dto.setHasLiked(Boolean.TRUE.equals(this.redisTemplate.hasKey(likeUserCommentKey)) ? 1 : 0);
        String likeCommentKey = "COMMENT_LIKE_" + dto.getId();
        String value = this.redisTemplate.opsForValue().get(likeCommentKey);
        if (StringUtils.isNotEmpty(value)) {
            dto.setLikeCount(Integer.valueOf(value));
        } else {
            dto.setLikeCount(0);
        }
        return dto;
    }
}
