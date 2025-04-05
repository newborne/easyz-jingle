package cn.easyz.server.service.v1;

import cn.easyz.common.model.vo.ResponseResult;

/**
 * The interface Huan xin service.
 */
public interface HuanXinService {
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
     * Contact users response result.
     *
     * @param userId   the user id
     * @param friendId the friend id
     * @return the response result
     */
    ResponseResult contactUsers(Long userId, Long friendId);
    /**
     * Send msg response result.
     *
     * @param target the target
     * @param msg    the msg
     * @param type   the type
     * @return the response result
     */
    ResponseResult sendMsg(String target, String msg, String type);
}
