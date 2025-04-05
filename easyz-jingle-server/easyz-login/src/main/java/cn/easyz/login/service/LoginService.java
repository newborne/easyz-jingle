package cn.easyz.login.service;

import cn.easyz.common.model.pojo.mysql.ApUser;
import cn.easyz.common.model.vo.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * The interface Login service.
 */
public interface LoginService {
    /**
     * Login response result.
     *
     * @param mobile the mobile
     * @param code   the code
     * @return the response result
     */
    ResponseResult login(String mobile, String code);
    /**
     * Query user by token ap user.
     *
     * @param token the token
     * @return the ap user
     */
    ApUser queryUserByToken(String token);
    /**
     * Save user info response result.
     *
     * @param param the param
     * @param token the token
     * @return the response result
     */
    ResponseResult saveUserInfo(Map<String, String> param, String token);
    /**
     * Save user logo response result.
     *
     * @param file  the file
     * @param token the token
     * @return the response result
     */
    ResponseResult saveUserLogo(MultipartFile file, String token);
    /**
     * Send to old mobile response result.
     *
     * @param token the token
     * @return the response result
     */
    ResponseResult sendToOldMobile(String token);
    /**
     * Update new mobile response result.
     *
     * @param token  the token
     * @param mobile the mobile
     * @return the response result
     */
    ResponseResult updateNewMobile(String token, String mobile);
}
