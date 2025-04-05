package cn.easyz.server.service.v1;

import cn.easyz.common.model.request.RecommendUserRequest;
import cn.easyz.common.model.vo.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * The interface Users service.
 */
public interface UsersService {
    /**
     * User response result.
     *
     * @return the response result
     */
    ResponseResult user();
    /**
     * Register response result.
     *
     * @param userId the user id
     * @return the response result
     */
    ResponseResult register(Long userId);
    /**
     * Send msg response result.
     *
     * @param target the target
     * @param msg    the msg
     * @param type   the type
     * @return the response result
     */
    ResponseResult sendMsg(String target, String msg, String type);
    /**
     * Save user info response result.
     *
     * @param param the param
     * @return the response result
     */
    ResponseResult saveUserInfo(Map<String, String> param);
    /**
     * Save user logo response result.
     *
     * @param file the file
     * @return the response result
     */
    ResponseResult saveUserLogo(MultipartFile file);
    /**
     * Query user info response result.
     *
     * @param userId the user id
     * @return the response result
     */
    ResponseResult queryUserInfo(Long userId);
    /**
     * Update user info response result.
     *
     * @param param the param
     * @return the response result
     */
    ResponseResult updateUserInfo(Map<String, String> param);
    /**
     * Add users response result.
     *
     * @param friendId the friend id
     * @return the response result
     */
    ResponseResult addUsers(Long friendId);
    /**
     * Query users list response result.
     *
     * @param page    the page
     * @param size    the size
     * @param keyword the keyword
     * @return the response result
     */
    ResponseResult queryUsersList(Integer page, Integer size, String keyword);
    /**
     * Query follow counts response result.
     *
     * @return the response result
     */
    ResponseResult queryFollowCounts();
    /**
     * Query follow list response result.
     *
     * @param type the type
     * @param page the page
     * @param size the size
     * @return the response result
     */
    ResponseResult queryFollowList(String type, Integer page, Integer size);
    /**
     * Follow response result.
     *
     * @param friendId the friend id
     * @return the response result
     */
    ResponseResult follow(Long friendId);
    /**
     * Un follow response result.
     *
     * @param friendId the friend id
     * @return the response result
     */
    ResponseResult unFollow(Long friendId);
    /**
     * Query today best response result.
     *
     * @return the response result
     */
    ResponseResult queryTodayBest();
    /**
     * Query recommend user response result.
     *
     * @param friendId the friend id
     * @return the response result
     */
    ResponseResult queryRecommendUser(Long friendId);
    /**
     * Query recommend user list response result.
     *
     * @param param the param
     * @return the response result
     */
    ResponseResult queryRecommendUserList(RecommendUserRequest param);
    /**
     * Query near user response result.
     *
     * @param sex      the sex
     * @param distance the distance
     * @return the response result
     */
    ResponseResult queryNearUser(String sex, String distance);
    /**
     * Update location response result.
     *
     * @param param the param
     * @return the response result
     */
    ResponseResult updateLocation(Map<String, Object> param);
}
