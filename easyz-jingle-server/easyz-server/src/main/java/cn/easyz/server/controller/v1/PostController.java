package cn.easyz.server.controller.v1;

import cn.easyz.common.model.vo.ResponseResult;
import cn.easyz.server.service.v1.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * The type Post controller.
 */
@RestController
@RequestMapping("api/v1/post")
public class PostController {
    @Autowired
    private PostService postService;
    /**
     * Save post response result.
     *
     * @param param the param
     * @return the response result
     */
    @PostMapping("publish")
    public ResponseResult savePost(@RequestBody Map<String, String> param) {
        return this.postService.savePost(param.get("text"),
                param.get("location"),
                param.get("longitude"),
                param.get("latitude"),
                null);
    }
    /**
     * Query friend post list response result.
     *
     * @param page the page
     * @param size the size
     * @return the response result
     */
    @GetMapping("friend")
    public ResponseResult queryFriendPostList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                              @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return this.postService.queryFriendPostList(page, size);
    }
    /**
     * Query recommend post list response result.
     *
     * @param page the page
     * @param size the size
     * @return the response result
     */
    @GetMapping("recommend")
    public ResponseResult queryRecommendPostList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                 @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return this.postService.queryRecommendPostList(page, size);
    }
    /**
     * Query user post list response result.
     *
     * @param userId the user id
     * @param page   the page
     * @param size   the size
     * @return the response result
     */
    @GetMapping("user/{userId}")
    public ResponseResult queryUserPostList(@PathVariable("userId") Long userId,
                                            @RequestParam(value = "page", defaultValue = "1") Integer page,
                                            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return this.postService.queryUserPostList(userId, page, size);
    }
    /**
     * Query post by id response result.
     *
     * @param id the id
     * @return the response result
     */
    @GetMapping("{id}")
    public ResponseResult queryPostById(@PathVariable("id") String id) {
        return this.postService.queryPostById(id);
    }


     @PostMapping("generateTitle")
    public ResponseResult generateTitle(@RequestBody Map<String, String> param) {
        String text = param.get("text");
        return this.postService.generateTitle(text);
    }
}
