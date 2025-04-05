package cn.easyz.dubbo.api.v1;

import cn.easyz.common.model.pojo.mongodb.Article;

import java.util.List;

/**
 * The interface Article api.
 */
public interface ArticleApi {
    /**
     * Save article string.
     *
     * @param article the article
     * @return the string
     */
    String saveArticle(Article article);
    /**
     * Query article list list.
     *
     * @param articleType the article type
     * @param page        the page
     * @param size        the size
     * @return the list
     */
    List<Article> queryArticleList(Integer articleType, Integer page, Integer size);
    /**
     * Query recommend article list list.
     *
     * @param userId the user id
     * @param page   the page
     * @param size   the size
     * @return the list
     */
    List<Article> queryRecommendArticleList(Long userId, Integer page, Integer size);
    /**
     * Query article by article rid article.
     *
     * @param articleRid the article rid
     * @return the article
     */
    Article queryArticleByArticleRid(String articleRid);
    /**
     * Query article by id article.
     *
     * @param id the id
     * @return the article
     */
    Article queryArticleById(String id);

}
