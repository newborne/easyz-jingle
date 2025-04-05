package cn.easyz.server.controller.v2;

import cn.easyz.common.model.vo.ResponseResult;
import cn.easyz.server.service.v2.MaterialServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * The type Material controller v 2.
 */
@RestController
@RequestMapping("api/v2/material")
public class MaterialControllerV2 {
    @Autowired
    private MaterialServiceV2 materialService;
    /**
     * Query material by material rid response result.
     *
     * @param materialRid the material rid
     * @return the response result
     */
    @GetMapping("{materialRid}")
    public ResponseResult queryMaterialByMaterialRid(@PathVariable("materialRid") Long materialRid) {
        return this.materialService.queryMaterialByMaterialRid(materialRid);
    }
    /**
     * Query recommend material list response result.
     *
     * @param page the page
     * @param size the size
     * @return the response result
     */
    @GetMapping("recommend")
    public ResponseResult queryRecommendMaterialList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                     @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return this.materialService.queryRecommendMaterialList(page, size);
    }
}
