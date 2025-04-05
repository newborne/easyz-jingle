package cn.easyz.server.service.v2;

import cn.easyz.common.model.vo.ResponseResult;

/**
 * The interface Material service v 2.
 */
public interface MaterialServiceV2 {
    /**
     * Query recommend material list response result.
     *
     * @param page the page
     * @param size the size
     * @return the response result
     */
    ResponseResult queryRecommendMaterialList(Integer page, Integer size);
    /**
     * Query material by material rid response result.
     *
     * @param materialRid the material rid
     * @return the response result
     */
    ResponseResult queryMaterialByMaterialRid(Long materialRid);
}
