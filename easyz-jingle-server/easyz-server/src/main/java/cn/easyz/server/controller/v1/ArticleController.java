package cn.easyz.server.controller.v1;

import cn.easyz.common.model.vo.ResponseResult;
import cn.easyz.server.service.v1.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * The type Article controller.
 */
@RestController
@RequestMapping("api/v1/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    /**
     * Save article response result.
     *
     * @param text        the text
     * @param title       the title
     * @param tags        the tags
     * @param articleType the article type
     * @return the response result
     */
    @PostMapping("save")
    public ResponseResult saveArticle(@RequestParam(value = "text") String text,
                                      @RequestParam(value = "title") String title,
                                      @RequestParam(value = "tags") String[] tags,
                                      @RequestParam(value = "articleType") Integer articleType) {
        return this.articleService.saveArticle(text, title, tags, articleType);
    }
    /**
     * Query article list response result.
     *
     * @param articleType the article type
     * @param page        the page
     * @param size        the size
     * @return the response result
     */
    @GetMapping("list/{articleType}")
    public ResponseResult queryArticleList(@PathVariable("articleType") Integer articleType,
                                           @RequestParam(value = "page", defaultValue = "1") Integer page,
                                           @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return this.articleService.queryArticleList(articleType, page, size);
    }
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
     * Query article by article rid response result.
     *
     * @param articleRid the article rid
     * @return the response result
     */
    @GetMapping("{articleRid}")
    public ResponseResult queryArticleByArticleRid(@PathVariable("articleRid") String articleRid) {
        return this.articleService.queryArticleByArticleRid(articleRid);
    }

}
