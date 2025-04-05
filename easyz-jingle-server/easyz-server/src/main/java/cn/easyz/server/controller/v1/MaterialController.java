package cn.easyz.server.controller.v1;

import cn.easyz.common.model.vo.ResponseResult;
import cn.easyz.server.service.v1.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * The type Material controller.
 */
@RestController
@RequestMapping("api/v1/material")
public class MaterialController {
    @Autowired
    private MaterialService materialService;
    /**
     * Save material response result.
     *
     * @param text         the text
     * @param tags         the tags
     * @param materialType the material type
     * @return the response result
     */
    @PostMapping("save")
    public ResponseResult saveMaterial(@RequestParam(value = "text") String text,
                                       @RequestParam(value = "tags") String[] tags,
                                       @RequestParam(value = "materialType") Integer materialType) {
        return this.materialService.saveMaterial(text, tags, materialType);
    }
    /**
     * Query material list response result.
     *
     * @param materialType the material type
     * @param page         the page
     * @param size         the size
     * @return the response result
     */
    @GetMapping("list/{materialType}")
    public ResponseResult queryMaterialList(@PathVariable("materialType") Integer materialType,
                                            @RequestParam(value = "page", defaultValue = "1") Integer page,
                                            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return this.materialService.queryMaterialList(materialType, page, size);
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
}
