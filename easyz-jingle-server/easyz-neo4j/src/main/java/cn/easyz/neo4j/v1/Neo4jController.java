package cn.easyz.neo4j.v1;

import cn.easyz.common.model.pojo.neo4j.node.ArticleNode;
import cn.easyz.common.model.vo.ResponseResult;
import cn.easyz.neo4j.service.ArticleService;
import cn.easyz.neo4j.service.MaterialService;
import cn.easyz.neo4j.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Neo 4 j controller.
 */
@RestController
@RequestMapping("api/v1/neo4j")
public class Neo4jController {
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private MaterialService materialService;
    /**
     * Save user response result.
     *
     * @return the response result
     */
    @GetMapping("saveUser")
    public ResponseResult saveUser() {
        return this.userService.saveUser();
    }
    /**
     * Save user recommend relationship response result.
     *
     * @return the response result
     */
    @GetMapping("saveUserRecommend")
    public ResponseResult saveUserRecommendRelationship() {
        return this.userService.saveUserRecommendRelationship();
    }
    /**
     * Save article type response result.
     *
     * @return the response result
     */
    @GetMapping("saveArticleType")
    public ResponseResult saveArticleType() {
        return this.articleService.saveArticleType();
    }
    /**
     * Save article response result.
     *
     * @return the response result
     */
    @GetMapping("saveArticle")
    public ResponseResult saveArticle() {
        return this.articleService.saveArticle();
    }
    /**
     * Save material type response result.
     *
     * @return the response result
     */
    @GetMapping("saveMaterialType")
    public ResponseResult saveMaterialType() {
        return this.materialService.saveMaterialType();
    }
    /**
     * Save material response result.
     *
     * @return the response result
     */
    @GetMapping("saveMaterial")
    public ResponseResult saveMaterial() {
        return this.materialService.saveMaterial();
    }
    /**
     * Save article recommend relationship response result.
     *
     * @return the response result
     */
    @GetMapping("saveArticleRecommend")
    public ResponseResult saveArticleRecommendRelationship() {
        return this.articleService.saveArticleRecommendRelationship();
    }
    /**
     * Save material recommend relationship response result.
     *
     * @return the response result
     */
    @GetMapping("saveMaterialRecommend")
    public ResponseResult saveMaterialRecommendRelationship() {
        return this.materialService.saveMaterialRecommendRelationship();
    }
    /**
     * Query recommend article list list.
     *
     * @param userId the user id
     * @param page   the page
     * @param size   the size
     * @return the list
     */
    @GetMapping("queryRecommendArticleList/{userId}")
    public List<ArticleNode> queryRecommendArticleList(@PathVariable("userId") Integer userId,
                                                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return this.articleService.queryRecommendArticleList(userId, page, size);
    }
}
