package cn.easyz.common.model.pojo.neo4j.relationship;

import cn.easyz.common.model.pojo.neo4j.node.ArticleNode;
import cn.easyz.common.model.pojo.neo4j.node.UserNode;
import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

/**
 * The type Article recommend relationship.
 */
@Data
@Builder
@RelationshipEntity(type = "article_recommend")
public class ArticleRecommendRelationship {
    @Id
    @GeneratedValue
    private Long id;
    @StartNode
    private ArticleNode start;
    @EndNode
    private UserNode end;
    // 备注
    private String description;
}
