package cn.easyz.neo4j.service;

import cn.easyz.common.model.vo.ResponseResult;

/**
 * The interface User service.
 */
public interface UserService {
    /**
     * Save user response result.
     *
     * @return the response result
     */
    ResponseResult saveUser();
    /**
     * Save user recommend relationship response result.
     *
     * @return the response result
     */
    ResponseResult saveUserRecommendRelationship();
}
