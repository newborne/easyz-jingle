package cn.easyz.common.model.pojo.neo4j.relationship;

import cn.easyz.common.model.pojo.neo4j.node.MaterialNode;
import cn.easyz.common.model.pojo.neo4j.node.MaterialTypeNode;
import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

/**
 * The type Material belong relationship.
 */
@Data
@Builder
@RelationshipEntity(type = "material_belong")
public class MaterialBelongRelationship {
    @Id
    @GeneratedValue
    private Long id;
    @StartNode
    private MaterialNode start;
    @EndNode
    private MaterialTypeNode end;
    // 备注
    private String description;
}
