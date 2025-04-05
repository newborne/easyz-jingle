package cn.easyz.server.controller.v2;

import cn.easyz.common.model.vo.ResponseResult;
import cn.easyz.server.service.v2.ArticleServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * The type Article controller v 2.
 */
@RestController
@RequestMapping("api/v2/article")
public class ArticleControllerV2 {
    @Autowired
    private ArticleServiceV2 articleService;
    /**
     * Query recommend article list response result.
     *
     * @param page the page
     * @param size the size
     * @return the response result
     */
    @GetMapping("recommend")
    public ResponseResult queryRecommendArticleList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                    @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return this.articleService.queryRecommendArticleList(page, size);
    }
    /**
     * Query article by articler rid response result.
     *
     * @param articleRid the article rid
     * @return the response result
     */
    @GetMapping("{articleRid}")
    public ResponseResult queryArticleByArticlerRid(@PathVariable("articleRid") String articleRid) {
        return this.articleService.queryArticleByArticleRid(articleRid);
    }
}
