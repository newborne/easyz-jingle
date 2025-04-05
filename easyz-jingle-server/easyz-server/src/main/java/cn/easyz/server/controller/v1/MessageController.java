package cn.easyz.server.controller.v1;

import cn.easyz.common.model.vo.ResponseResult;
import cn.easyz.server.service.v1.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * The type Message controller.
 */
@RestController
@RequestMapping("api/v1/message")
public class MessageController {
    @Autowired
    private MessageService messageService;
    /**
     * Query message comment list response result.
     *
     * @param commentType the comment type
     * @param page        the page
     * @param size        the size
     * @return the response result
     */
    @GetMapping("{commentType}")
    public ResponseResult queryMessageCommentList(
            @PathVariable("commentType") Integer commentType,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return this.messageService.queryMessageCommentList(commentType, page, size);
    }
    /**
     * Query message announcement list response result.
     *
     * @param page the page
     * @param size the size
     * @return the response result
     */
    @GetMapping("announcement")
    public ResponseResult queryMessageAnnouncementList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return this.messageService.queryMessageAnnouncementList(page, size);
    }
}
