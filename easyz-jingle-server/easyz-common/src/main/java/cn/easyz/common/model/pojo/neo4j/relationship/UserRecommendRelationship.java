package cn.easyz.common.model.pojo.neo4j.relationship;

import cn.easyz.common.model.pojo.neo4j.node.UserNode;
import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

/**
 * The type User recommend relationship.
 */
@Data
@Builder
@RelationshipEntity(type = "user_recommend")
public class UserRecommendRelationship {
    @Id
    @GeneratedValue
    private Long id;
    // friendId
    @StartNode
    private UserNode start;
    // userId
    @EndNode
    private UserNode end;
    //相似度
    private Double similarity;
    // 备注
    private String description;
}
