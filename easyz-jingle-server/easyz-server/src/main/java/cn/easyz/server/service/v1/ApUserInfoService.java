package cn.easyz.server.service.v1;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.easyz.common.model.pojo.mysql.ApUserInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * The interface Ap user info service.
 */
public interface ApUserInfoService {
    /**
     * Save user info boolean.
     *
     * @param param the param
     * @return the boolean
     */
    Boolean saveUserInfo(Map<String, String> param);
    /**
     * Save user logo boolean.
     *
     * @param file the file
     * @return the boolean
     */
    Boolean saveUserLogo(MultipartFile file);
    /**
     * Query user info by user id ap user info.
     *
     * @param userId the user id
     * @return the ap user info
     */
    ApUserInfo queryUserInfoByUserId(Long userId);
    /**
     * Query user info list list.
     *
     * @param queryWrapper the query wrapper
     * @return the list
     */
    List<ApUserInfo> queryUserInfoList(QueryWrapper queryWrapper);
    /**
     * Update user info boolean.
     *
     * @param userInfo the user info
     * @return the boolean
     */
    Boolean updateUserInfo(ApUserInfo userInfo);
}
