package cn.easyz.dubbo.api.v1;

import cn.easyz.common.model.pojo.mongodb.Comment;

import java.util.List;

/**
 * The interface Comment api.
 */
public interface CommentApi {
    /**
     * Save comment boolean.
     *
     * @param userId    the user id
     * @param publishId the publish id
     * @param type      the type
     * @param text      the text
     * @return the boolean
     */
// 评论点赞喜欢
    Boolean saveComment(Long userId, String publishId, Integer type, String text);
    /**
     * Remove comment boolean.
     *
     * @param userId      the user id
     * @param publishId   the publish id
     * @param commentType the comment type
     * @return the boolean
     */
// 取消点赞喜欢
    Boolean removeComment(Long userId, String publishId, Integer commentType);
    /**
     * Query comment count long.
     *
     * @param publishId the publish id
     * @param type      the type
     * @return the long
     */
// 查看评论数量
    Long queryCommentCount(String publishId, Integer type);
    /**
     * Query text comment list list.
     *
     * @param publishId the publish id
     * @param page      the page
     * @param size      the size
     * @return the list
     */
// 查看评论列表
    List<Comment> queryTextCommentList(String publishId, Integer page, Integer size);
    /**
     * Query comment list by publish user id list.
     *
     * @param publishUserId the publish user id
     * @param type          the type
     * @param page          the page
     * @param size          the size
     * @return the list
     */
    List<Comment> queryCommentListByPublishUserId(Long publishUserId, Integer type, Integer page, Integer size);
}
