package cn.easyz.neo4j.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.easyz.common.model.mapper.ApDictionaryMapper;
import cn.easyz.common.model.pojo.mongodb.Material;
import cn.easyz.common.model.pojo.mysql.ApDictionary;
import cn.easyz.common.model.pojo.neo4j.node.MaterialNode;
import cn.easyz.common.model.pojo.neo4j.node.MaterialTypeNode;
import cn.easyz.common.model.pojo.neo4j.node.UserNode;
import cn.easyz.common.model.pojo.neo4j.relationship.MaterialBelongRelationship;
import cn.easyz.common.model.pojo.neo4j.relationship.MaterialRecommendRelationship;
import cn.easyz.common.model.repository.*;
import cn.easyz.common.model.vo.ResponseResult;
import cn.easyz.neo4j.service.MaterialService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Material service.
 */
@Service
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    private MaterialTypeRepository materialTypeRepository;
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private MaterialBelongRelationshipRepository materialBelongRelationshipRepository;
    @Autowired
    private MaterialRecommendRelationshipRepository materialRecommendRelationshipRepository;
    @Resource
    private ApDictionaryMapper apDictionaryMapper;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private UserRepository userRepository;
    @Override
    public ResponseResult saveMaterialType() {
        List<ApDictionary> materialTypeList = this.apDictionaryMapper.selectList(new QueryWrapper<ApDictionary>().eq(
                "parent_id",
                20000));
        for (ApDictionary materialType : materialTypeList) {
            MaterialTypeNode materialTypeNode = MaterialTypeNode.builder()
                    .materialType(materialType.getValue())
                    .materialTypeName(materialType.getName())
                    .build();
            this.materialTypeRepository.save(materialTypeNode);
        }
        return ResponseResult.ok();
    }
    @Override
    public ResponseResult saveMaterial() {
        this.materialTypeRepository.findAll().forEach(materialTypeNode -> {
            Query query = Query.query(Criteria.where("materialType").is(materialTypeNode.getMaterialType()));
            List<Material> materialList = this.mongoTemplate.find(query, Material.class);
            for (Material material : materialList) {
                MaterialNode materialNode = MaterialNode.builder()
                        .materialRid(material.getMaterialRid())
                        .materialType(material.getMaterialType())
                        .tags(material.getTags())
                        .text(material.getText()).build();
                this.materialRepository.save(materialNode);
                MaterialBelongRelationship materialBelongRelationship = MaterialBelongRelationship.builder()
                        .start(materialNode)
                        .end(materialTypeNode)
                        .description("属于").build();
                this.materialBelongRelationshipRepository.save(materialBelongRelationship);
            }
        });
        return ResponseResult.ok();
    }
    @Override
    public ResponseResult saveMaterialRecommendRelationship() {
        Iterable<UserNode> userNodes = userRepository.findAll();
        for (UserNode userNode : userNodes) {
            String key = "RECOMMEND_MATERIAL_" + userNode.getUserId();
            String value = this.redisTemplate.opsForValue().get(key);
            if (StringUtils.isNotEmpty(value)) {
                //命中了数据
                String[] materialRids = StringUtils.split(value, ",");
                List<Long> materialRidList = new ArrayList<>();
                for (int i = 0; i < materialRids.length; i++) {
                    materialRidList.add(Long.valueOf(materialRids[i]));
                }
                for (Long materialRid : materialRidList) {
                    MaterialRecommendRelationship materialRecommendRelationship = MaterialRecommendRelationship.builder()
                            .start(this.materialRepository.findByMaterialRid(materialRid))
                            .end(userNode)
                            .description("推荐").build();
                    this.materialRecommendRelationshipRepository.save(materialRecommendRelationship);
                }
            }
        }
        return ResponseResult.ok();
    }
    @Override
    public ResponseResult queryRecommendMaterial(Long userId) {
        return null;
    }
}
