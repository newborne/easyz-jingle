package cn.easyz.server.service.v1;

import cn.easyz.common.model.vo.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * The interface Video service.
 */
public interface VideoService {
    /**
     * Save video response result.
     *
     * @param text      the text
     * @param picFile   the pic file
     * @param videoFile the video file
     * @return the response result
     * @throws IOException the io exception
     */
    ResponseResult saveVideo(String text, MultipartFile picFile, MultipartFile videoFile) throws IOException;
    /**
     * Query friend video list response result.
     *
     * @param page the page
     * @param size the size
     * @return the response result
     */
    ResponseResult queryFriendVideoList(Integer page, Integer size);
    /**
     * Query recommend video list response result.
     *
     * @param page the page
     * @param size the size
     * @return the response result
     */
    ResponseResult queryRecommendVideoList(Integer page, Integer size);
    /**
     * Query user video list response result.
     *
     * @param userId the user id
     * @param page   the page
     * @param size   the size
     * @return the response result
     */
    ResponseResult queryUserVideoList(Long userId, Integer page, Integer size);
    /**
     * Query video by id response result.
     *
     * @param publishId the publish id
     * @return the response result
     */
    ResponseResult queryVideoById(String publishId);
}
