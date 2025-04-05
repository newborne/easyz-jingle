package cn.easyz.common.model.pojo.neo4j.relationship;

import cn.easyz.common.model.pojo.neo4j.node.MaterialNode;
import cn.easyz.common.model.pojo.neo4j.node.UserNode;
import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

/**
 * The type Material recommend relationship.
 */
@Data
@Builder
@RelationshipEntity(type = "material_recommend")
public class MaterialRecommendRelationship {
    @Id
    @GeneratedValue
    private Long id;
    @StartNode
    private MaterialNode start;
    @EndNode
    private UserNode end;
    // 备注
    private String description;
}
