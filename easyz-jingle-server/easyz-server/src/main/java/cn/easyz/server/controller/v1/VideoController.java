package cn.easyz.server.controller.v1;

import cn.easyz.common.model.vo.ResponseResult;
import cn.easyz.server.service.v1.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * The type Video controller.
 */
@RestController
@RequestMapping("api/v1/video")
public class VideoController {
    @Autowired
    private VideoService videoService;
    /**
     * Save video response result.
     *
     * @param text      the text
     * @param picFile   the pic file
     * @param videoFile the video file
     * @return the response result
     * @throws IOException the io exception
     */
    @PostMapping("publish")
    public ResponseResult saveVideo(@RequestParam(value = "text") String text,
                                    @RequestParam(value = "picFile", required = false) MultipartFile picFile,
                                    @RequestParam(value = "videoFile", required = false) MultipartFile videoFile) throws IOException {
        return this.videoService.saveVideo(text,
                picFile, videoFile);
    }
    /**
     * Query friend video list response result.
     *
     * @param page the page
     * @param size the size
     * @return the response result
     */
    @GetMapping("friend")
    public ResponseResult queryFriendVideoList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                               @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return this.videoService.queryFriendVideoList(page, size);
    }
    /**
     * Query recommend video list response result.
     *
     * @param page the page
     * @param size the size
     * @return the response result
     */
    @GetMapping("recommend")
    public ResponseResult queryRecommendVideoList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                  @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return this.videoService.queryRecommendVideoList(page, size);
    }
    /**
     * Query user video list response result.
     *
     * @param userId the user id
     * @param page   the page
     * @param size   the size
     * @return the response result
     */
    @GetMapping("user/{userId}")
    public ResponseResult queryUserVideoList(
            @PathVariable("userId") Long userId, @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return this.videoService.queryUserVideoList(userId, page, size);
    }
    /**
     * Query video by id response result.
     *
     * @param id the id
     * @return the response result
     */
    @GetMapping("{id}")
    public ResponseResult queryVideoById(@PathVariable("id") String id) {
        return this.videoService.queryVideoById(id);
    }
}
