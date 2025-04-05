package cn.easyz.common.model.pojo.neo4j.node;

import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * The type Material type node.
 */
@Data
@Builder
@NodeEntity(label = "MaterialTypeNode")
public class MaterialTypeNode {
    @Id
    @GeneratedValue
    private Long id;
    @Property(name = "material_type")
    private Integer materialType;
    @Property(name = "material_type_name")
    private String materialTypeName;
}
