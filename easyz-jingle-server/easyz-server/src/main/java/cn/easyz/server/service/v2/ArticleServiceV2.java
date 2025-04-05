package cn.easyz.server.service.v2;

import cn.easyz.common.model.vo.ResponseResult;

/**
 * The interface Article service v 2.
 */
public interface ArticleServiceV2 {
    /**
     * Query recommend article list response result.
     *
     * @param page the page
     * @param size the size
     * @return the response result
     */
    ResponseResult queryRecommendArticleList(Integer page, Integer size);
    /**
     * Query article by article rid response result.
     *
     * @param articleRid the article rid
     * @return the response result
     */
    ResponseResult queryArticleByArticleRid(String articleRid);
}
