package cn.easyz.dubbo.api.v1;

import cn.easyz.common.model.pojo.mongodb.Material;

import java.util.List;

/**
 * The interface Material api.
 */
public interface MaterialApi {
    /**
     * Save material string.
     *
     * @param material the material
     * @return the string
     */
    String saveMaterial(Material material);
    /**
     * Query recommend material list list.
     *
     * @param userId the user id
     * @param page   the page
     * @param size   the size
     * @return the list
     */
    List<Material> queryRecommendMaterialList(Long userId, Integer page, Integer size);
    /**
     * Query material by id material.
     *
     * @param id the id
     * @return the material
     */
    Material queryMaterialById(String id);
    /**
     * Query material by material rid material.
     *
     * @param materialRid the material rid
     * @return the material
     */
    Material queryMaterialByMaterialRid(Long materialRid);
    /**
     * Query material list list.
     *
     * @param materialType the material type
     * @param page         the page
     * @param size         the size
     * @return the list
     */
    List<Material> queryMaterialList(Integer materialType, Integer page, Integer size);
}
