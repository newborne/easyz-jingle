package cn.easyz.common.model.pojo.neo4j.node;

import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * The type Article node.
 */
@Data
@Builder
@NodeEntity(label = "ArticleNode")
public class ArticleNode {
    @Id
    @GeneratedValue
    private Long id;
    @Property(name = "article_rid ")
    private Long articleRid;
    private String title;
    @Property(name = "article_type ")
    private Integer articleType;
    private String[] tags;
    private String text; //文字
}
