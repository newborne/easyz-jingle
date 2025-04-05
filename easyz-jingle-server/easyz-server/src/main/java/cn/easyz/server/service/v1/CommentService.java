package cn.easyz.server.service.v1;

import cn.easyz.common.model.vo.ResponseResult;

/**
 * The interface Comment service.
 */
public interface CommentService {
    /**
     * Like comment response result.
     *
     * @param destination the destination
     * @param publishId   the publish id
     * @return the response result
     */
    ResponseResult likeComment(String destination, String publishId);
    /**
     * Dis like comment response result.
     *
     * @param destination the destination
     * @param publishId   the publish id
     * @return the response result
     */
    ResponseResult disLikeComment(String destination, String publishId);
    /**
     * Love comment response result.
     *
     * @param destination the destination
     * @param publishId   the publish id
     * @return the response result
     */
    ResponseResult loveComment(String destination, String publishId);
    /**
     * Un love comment response result.
     *
     * @param destination the destination
     * @param publishId   the publish id
     * @return the response result
     */
    ResponseResult unLoveComment(String destination, String publishId);
    /**
     * Text comment response result.
     *
     * @param destination the destination
     * @param publishId   the publish id
     * @param text        the text
     * @return the response result
     */
    ResponseResult textComment(String destination, String publishId, String text);
    /**
     * Query text comment list response result.
     *
     * @param publishId the publish id
     * @param page      the page
     * @param size      the size
     * @return the response result
     */
    ResponseResult queryTextCommentList(String publishId, Integer page, Integer size);
}
