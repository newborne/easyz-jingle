package cn.easyz.login.service;

import cn.easyz.common.model.vo.ResponseResult;

/**
 * The interface Sms service.
 */
public interface SmsService {
    /**
     * Send check code response result.
     *
     * @param mobile the mobile
     * @return the response result
     */
    ResponseResult sendCheckCode(String mobile);
}
