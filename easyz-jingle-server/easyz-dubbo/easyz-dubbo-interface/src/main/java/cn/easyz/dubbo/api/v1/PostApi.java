package cn.easyz.dubbo.api.v1;

import cn.easyz.common.model.pojo.mongodb.Post;

import java.util.List;

/**
 * The interface Post api.
 */
public interface PostApi {
    /**
     * Save post string.
     *
     * @param post the post
     * @return the string
     */
    // 发帖
    String savePost(Post post);

    /**
     * Query post by id post.
     *
     * @param id the id
     * @return the post
     */
    // 查询帖子
    Post queryPostById(String id);

    /**
     * Query friend post list list.
     *
     * @param userId the user id
     * @param page   the page
     * @param size   the size
     * @return the list
     */
    // 查好友帖子-时间线
    List<Post> queryFriendPostList(Long userId, Integer page, Integer size);

    /**
     * Query user post list list.
     *
     * @param userId the user id
     * @param page   the page
     * @param size   the size
     * @return the list
     */
    // 查自己帖子列表
    List<Post> queryUserPostList(Long userId, Integer page, Integer size);

    /**
     * Query recommend post list list.
     *
     * @param userId the user id
     * @param page   the page
     * @param size   the size
     * @return the list
     */
    // 查推荐帖子列表
    List<Post> queryRecommendPostList(Long userId, Integer page, Integer size);

    /**
     * Generate title string.
     *
     * @param text the text
     * @return the title
     */
    // 生成标题
    String generateTitle(String text);
}
