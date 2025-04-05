package cn.easyz.server.service.v1;

import cn.easyz.common.model.vo.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * The interface Post service.
 */
public interface PostService {
    /**
     * Save post response result.
     *
     * @param text          the text
     * @param location      the location
     * @param longitude     the longitude
     * @param latitude      the latitude
     * @param multipartFile the multipart file
     * @return the response result
     */
    ResponseResult savePost(String text,
                            String location,
                            String longitude,
                            String latitude,
                            MultipartFile[] multipartFile);
    /**
     * Query friend post list response result.
     *
     * @param page the page
     * @param size the size
     * @return the response result
     */
    ResponseResult queryFriendPostList(Integer page, Integer size);
    /**
     * Query recommend post list response result.
     *
     * @param page the page
     * @param size the size
     * @return the response result
     */
    ResponseResult queryRecommendPostList(Integer page, Integer size);
    /**
     * Query user post list response result.
     *
     * @param userId the user id
     * @param page   the page
     * @param size   the size
     * @return the response result
     */
    ResponseResult queryUserPostList(Long userId, Integer page, Integer size);
    /**
     * Query post by id response result.
     *
     * @param publishId the publish id
     * @return the response result
     */
    ResponseResult queryPostById(String publishId);
    // ResponseResult queryRecommendPostList(Integer page, Integer size);
    /**
     * 生成文章摘要和标题。
     *
     * @param text 文章内容
     * @return 包含生成的摘要和标题的响应结果
     */
    ResponseResult generateTitle(String text);
}
