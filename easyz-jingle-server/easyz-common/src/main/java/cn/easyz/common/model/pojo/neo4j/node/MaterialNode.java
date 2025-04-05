package cn.easyz.common.model.pojo.neo4j.node;

import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * The type Material node.
 */
@Data
@Builder
@NodeEntity(label = "MaterialNode")
public class MaterialNode {
    @Id
    @GeneratedValue
    private Long id;
    @Property(name = "material_rid")
    private Long materialRid;
    @Property(name = "material_type")
    private Integer materialType;
    private String[] tags;
    private String text;
}
