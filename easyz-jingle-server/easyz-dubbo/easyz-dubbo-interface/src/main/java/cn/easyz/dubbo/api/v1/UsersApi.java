package cn.easyz.dubbo.api.v1;

import cn.easyz.common.model.dto.FollowCountDTO;
import cn.easyz.common.model.dto.PageInfoDTO;
import cn.easyz.common.model.dto.UserLocationDTO;
import cn.easyz.common.model.pojo.mongodb.FollowUser;
import cn.easyz.common.model.pojo.mongodb.RecommendUser;
import cn.easyz.common.model.pojo.mongodb.Users;

import java.util.List;

/**
 * The interface Users api.
 */
public interface UsersApi {
    /**
     * Save users string.
     *
     * @param users the users
     * @return the string
     */
// 好友
    String saveUsers(Users users);
    /**
     * Query users list list.
     *
     * @param userId the user id
     * @return the list
     */
    List<Users> queryUsersList(Long userId);
    /**
     * Query users list list.
     *
     * @param userId the user id
     * @param page   the page
     * @param size   the size
     * @return the list
     */
    List<Users> queryUsersList(Long userId, Integer page, Integer size);
    /**
     * Follow user string.
     *
     * @param userId   the user id
     * @param friendId the friend id
     * @return the string
     */
// 关注
    String followUser(Long userId, Long friendId);
    /**
     * Is mutual follow boolean.
     *
     * @param userId   the user id
     * @param friendId the friend id
     * @return the boolean
     */
    Boolean isMutualFollow(Long userId, Long friendId);
    /**
     * Delete follow user boolean.
     *
     * @param userId   the user id
     * @param friendId the friend id
     * @return the boolean
     */
    Boolean deleteFollowUser(Long userId, Long friendId);
    /**
     * Query mutual follow list list.
     *
     * @param userId the user id
     * @param page   the page
     * @param size   the size
     * @return the list
     */
    List<FollowUser> queryMutualFollowList(Long userId, Integer page, Integer size);
    /**
     * Query follow list list.
     *
     * @param userId the user id
     * @param page   the page
     * @param size   the size
     * @return the list
     */
    List<FollowUser> queryFollowList(Long userId, Integer page, Integer size);
    /**
     * Query fans list list.
     *
     * @param userId the user id
     * @param page   the page
     * @param size   the size
     * @return the list
     */
    List<FollowUser> queryFansList(Long userId, Integer page, Integer size);
    /**
     * Query follow counts follow count dto.
     *
     * @param userId the user id
     * @return the follow count dto
     */
    FollowCountDTO queryFollowCounts(Long userId);
    /**
     * Query with max similarity recommend user.
     *
     * @param userId the user id
     * @return the recommend user
     */
// 最佳推荐
    RecommendUser queryWithMaxSimilarity(Long userId);
    /**
     * Query recommend user list page info dto.
     *
     * @param userId the user id
     * @param page   the page
     * @param size   the size
     * @return the page info dto
     */
    PageInfoDTO<RecommendUser> queryRecommendUserList(Long userId, Integer page, Integer size);
    /**
     * Query similarity double.
     *
     * @param friendId the friend id
     * @param userId   the user id
     * @return the double
     */
    Double querySimilarity(Long friendId, Long userId);
    /**
     * Update location boolean.
     *
     * @param userId    the user id
     * @param longitude the longitude
     * @param latitude  the latitude
     * @param address   the address
     * @return the boolean
     */
// 地理位置
    Boolean updateLocation(Long userId, Double longitude, Double latitude, String address);
    /**
     * Query location by user id user location dto.
     *
     * @param userId the user id
     * @return the user location dto
     */
    UserLocationDTO queryLocationByUserId(Long userId);
    /**
     * Query user from location list.
     *
     * @param longitude the longitude
     * @param latitude  the latitude
     * @param range     the range
     * @return the list
     */
    List<UserLocationDTO> queryUserFromLocation(Double longitude, Double latitude, Integer range);
    /**
     * Query distance string.
     *
     * @param userId    the user id
     * @param longitude the longitude
     * @param latitude  the latitude
     * @return the string
     */
    String queryDistance(Long userId, String longitude, String latitude);
}
