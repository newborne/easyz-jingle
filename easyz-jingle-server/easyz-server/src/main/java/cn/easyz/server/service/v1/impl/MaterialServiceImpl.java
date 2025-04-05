package cn.easyz.server.service.v1.impl;

import cn.easyz.common.model.dto.MaterialDTO;
import cn.easyz.common.model.dto.PageInfoDTO;
import cn.easyz.common.model.pojo.mongodb.Material;
import cn.easyz.common.model.pojo.mysql.ApUser;
import cn.easyz.common.model.vo.ResponseResult;
import cn.easyz.common.util.UserThreadLocal;
import cn.easyz.dubbo.api.v1.MaterialApi;
import cn.easyz.server.service.v1.MaterialService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Material service.
 */
@Service
public class MaterialServiceImpl implements MaterialService {
    @DubboReference(version = "1.0.0")
    private MaterialApi materialApi;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Override
    public ResponseResult queryMaterialList(Integer materialType, Integer page, Integer size) {
        List<Material> materialList = this.materialApi.queryMaterialList(materialType, page, size);
        List<MaterialDTO> dtos = new ArrayList<>();
        for (Material material : materialList) {
            dtos.add(this.fillValueToMaterial(material));
        }
        return ResponseResult.ok(new PageInfoDTO<>(0, page, size, dtos));
    }
    @Override
    public ResponseResult queryRecommendMaterialList(Integer page, Integer size) {
        List<Material> materialList = this.materialApi.queryRecommendMaterialList(
                Long.valueOf(UserThreadLocal.get().getId()), page, size);
        List<MaterialDTO> dtos = new ArrayList<>();
        for (Material material : materialList) {
            dtos.add(this.fillValueToMaterial(material));
        }
        return ResponseResult.ok(new PageInfoDTO<>(0, page, size, dtos));
    }
    @Override
    public ResponseResult queryMaterialByMaterialRid(Long materialRid) {
        return ResponseResult.ok(this.fillValueToMaterial(this.materialApi.queryMaterialByMaterialRid(materialRid)));
    }
    @Override
    public ResponseResult saveMaterial(String text, String[] tags, Integer materialType) {
        Material material = new Material();
        material.setText(text);
        material.setTags(tags);
        material.setMaterialType(materialType);
        return (this.materialApi.saveMaterial(material) != null) ? ResponseResult.ok() : ResponseResult.fail();
    }
    private MaterialDTO fillValueToMaterial(Material material) {
        MaterialDTO dto = new MaterialDTO();
        dto.setId(String.valueOf(material.getId()));
        dto.setMaterialRid(String.valueOf(material.getMaterialRid()));
        dto.setText(material.getText());
        dto.setTags(material.getTags());
        // 填充评论相关
        ApUser user = UserThreadLocal.get();
        // 是否喜欢
        String loveUserCommentKey = "COMMENT_LOVE_USER_" + user.getId() + "_" + dto.getId();
        dto.setHasLoved(this.redisTemplate.hasKey(loveUserCommentKey) ? 1 : 0);
        String loveCommentKey = "COMMENT_LOVE_" + dto.getId();
        String loveValue = this.redisTemplate.opsForValue().get(loveCommentKey);
        if (StringUtils.isNotEmpty(loveValue)) {
            dto.setLoveCount(Integer.valueOf(loveValue));
        } else {
            dto.setLoveCount(0);
        }
        return dto;
    }
}
