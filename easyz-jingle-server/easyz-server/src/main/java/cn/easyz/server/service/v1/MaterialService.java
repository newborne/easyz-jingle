package cn.easyz.server.service.v1;

import cn.easyz.common.model.vo.ResponseResult;

/**
 * The interface Material service.
 */
public interface MaterialService {
    /**
     * Query material list response result.
     *
     * @param materialType the material type
     * @param page         the page
     * @param size         the size
     * @return the response result
     */
    ResponseResult queryMaterialList(Integer materialType, Integer page, Integer size);
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
    /**
     * Save material response result.
     *
     * @param text         the text
     * @param tags         the tags
     * @param materialType the material type
     * @return the response result
     */
    ResponseResult saveMaterial(String text, String[] tags, Integer materialType);
}
