package cn.easyz.server.controller.v1;

import cn.easyz.common.model.vo.ResponseResult;
import cn.easyz.server.service.v1.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * The type Comment controller.
 */
@RestController
@RequestMapping("api/v1/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    /**
     * Like comment response result.
     *
     * @param publishId   the publish id
     * @param destination the destination
     * @return the response result
     */
    @GetMapping("{destination}/{publishId}/like")
    public ResponseResult likeComment(@PathVariable("publishId") String publishId,
                                      @PathVariable("destination") String destination) {
        return this.commentService.likeComment(destination, publishId);
    }
    /**
     * Dis like comment response result.
     *
     * @param publishId   the publish id
     * @param destination the destination
     * @return the response result
     */
    @GetMapping("{destination}/{publishId}/dislike")
    public ResponseResult disLikeComment(@PathVariable("publishId") String publishId,
                                         @PathVariable("destination") String destination) {
        return this.commentService.disLikeComment(destination, publishId);
    }
    /**
     * Love comment response result.
     *
     * @param publishId   the publish id
     * @param destination the destination
     * @return the response result
     */
    @GetMapping("{destination}/{publishId}/love")
    public ResponseResult loveComment(@PathVariable("publishId") String publishId,
                                      @PathVariable("destination") String destination) {
        return this.commentService.loveComment(destination, publishId);
    }
    /**
     * Un love comment response result.
     *
     * @param publishId   the publish id
     * @param destination the destination
     * @return the response result
     */
    @GetMapping("{destination}/{publishId}/unlove")
    public ResponseResult unLoveComment(@PathVariable("publishId") String publishId,
                                        @PathVariable("destination") String destination) {
        return this.commentService.unLoveComment(destination, publishId);
    }
    /**
     * Text comment response result.
     *
     * @param publishId   the publish id
     * @param destination the destination
     * @param text        the text
     * @return the response result
     */
    @PostMapping("{destination}/{publishId}/text")
    public ResponseResult textComment(@PathVariable("publishId") String publishId,
                                      @PathVariable("destination") String destination,
                                      @RequestBody Map<String, String> param) {
        return this.commentService.textComment(destination, publishId, param.get("text"));
    }
    /**
     * Query text comment list response result.
     *
     * @param publishId the publish id
     * @param page      the page
     * @param size      the size
     * @return the response result
     */
    @GetMapping("{publishId}")
    public ResponseResult queryTextCommentList(@PathVariable("publishId") String publishId,
                                               @RequestParam(value = "page", defaultValue = "1") Integer page,
                                               @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return this.commentService.queryTextCommentList(publishId, page, size);
    }
}
