package cn.easyz.neo4j.service;

import cn.easyz.common.model.vo.ResponseResult;

/**
 * The interface Material service.
 */
public interface MaterialService {
    /**
     * Save material type response result.
     *
     * @return the response result
     */
    ResponseResult saveMaterialType();
    /**
     * Save material response result.
     *
     * @return the response result
     */
    ResponseResult saveMaterial();
    /**
     * Save material recommend relationship response result.
     *
     * @return the response result
     */
    ResponseResult saveMaterialRecommendRelationship();
    /**
     * Query recommend material response result.
     *
     * @param userId the user id
     * @return the response result
     */
    ResponseResult queryRecommendMaterial(Long userId);
}
