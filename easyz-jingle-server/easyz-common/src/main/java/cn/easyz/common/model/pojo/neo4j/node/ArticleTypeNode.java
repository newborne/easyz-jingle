package cn.easyz.common.model.pojo.neo4j.node;

import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * The type Article type node.
 */
@Data
@Builder
@NodeEntity(label = "ArticleTypeNode")
public class ArticleTypeNode {
    @Id
    @GeneratedValue
    private Long id;
    @Property(name = "article_type")
    private Integer articleType;
    @Property(name = "article_type_name")
    private String articleTypeName;
}
