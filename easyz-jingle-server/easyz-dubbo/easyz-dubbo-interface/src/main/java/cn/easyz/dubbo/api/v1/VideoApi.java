package cn.easyz.dubbo.api.v1;

import cn.easyz.common.model.pojo.mongodb.Video;

import java.util.List;

/**
 * The interface Video api.
 */
public interface VideoApi {
    /**
     * Save video string.
     *
     * @param video the video
     * @return the string
     */
// 发帖
    String saveVideo(Video video);
    /**
     * Query video by id video.
     *
     * @param id the id
     * @return the video
     */
// 查询帖子
    Video queryVideoById(String id);
    /**
     * Query friend video list list.
     *
     * @param userId the user id
     * @param page   the page
     * @param size   the size
     * @return the list
     */
// 查好友帖子-时间线
    List<Video> queryFriendVideoList(Long userId, Integer page, Integer size);
    /**
     * Query user video list list.
     *
     * @param userId the user id
     * @param page   the page
     * @param size   the size
     * @return the list
     */
// 查自己帖子列表
    List<Video> queryUserVideoList(Long userId, Integer page, Integer size);
    /**
     * Query recommend video list list.
     *
     * @param userId the user id
     * @param page   the page
     * @param size   the size
     * @return the list
     */
// 查推荐帖子列表
    List<Video> queryRecommendVideoList(Long userId, Integer page, Integer size);
}
