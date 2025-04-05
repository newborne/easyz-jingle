package cn.easyz.server.service.v1;

import cn.easyz.common.model.vo.ResponseResult;

/**
 * 文章服务接口。
 */
public interface ArticleService {

    /**
     * 查询推荐文章列表。
     *
     * @param page 当前页码
     * @param size 每页大小
     * @return 包含推荐文章列表的响应结果
     */
    ResponseResult queryRecommendArticleList(Integer page, Integer size);

    /**
     * 根据文章 RID 查询文章详情。
     *
     * @param articleRid 文章 RID
     * @return 包含文章详情的响应结果
     */
    ResponseResult queryArticleByArticleRid(String articleRid);

    /**
     * 查询文章列表。
     *
     * @param articleType 文章类型
     * @param page        当前页码
     * @param size        每页大小
     * @return 包含文章列表的响应结果
     */
    ResponseResult queryArticleList(Integer articleType, Integer page, Integer size);

    /**
     * 保存文章。
     *
     * @param text        文章内容
     * @param title       文章标题
     * @param tags        文章标签数组
     * @param articleType 文章类型
     * @return 保存文章后的响应结果
     */
    ResponseResult saveArticle(String text, String title, String[] tags, Integer articleType);


}
