package cn.easyz.neo4j.service;

import cn.easyz.common.model.pojo.neo4j.node.ArticleNode;
import cn.easyz.common.model.vo.ResponseResult;

import java.util.List;

/**
 * The interface Article service.
 */
public interface ArticleService {
    /**
     * Save article type response result.
     *
     * @return the response result
     */
    ResponseResult saveArticleType();
    /**
     * Save article response result.
     *
     * @return the response result
     */
    ResponseResult saveArticle();
    /**
     * Save article recommend relationship response result.
     *
     * @return the response result
     */
    ResponseResult saveArticleRecommendRelationship();
    /**
     * Query recommend article list list.
     *
     * @param userId the user id
     * @param page   the page
     * @param size   the size
     * @return the list
     */
    List<ArticleNode> queryRecommendArticleList(Integer userId, Integer page, Integer size);
}
