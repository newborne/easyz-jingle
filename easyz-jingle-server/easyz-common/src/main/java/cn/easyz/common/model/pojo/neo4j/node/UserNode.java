package cn.easyz.common.model.pojo.neo4j.node;

import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * The type User node.
 */
@Data
@Builder
@NodeEntity(label = "UserNode")
public class UserNode {
    @Id
    @GeneratedValue
    private Long id;
    @Property(name = "user_id")
    private Integer userId;
    @Property(name = "nick_name")
    private String nickName;
    private String logo;
    private String tags;
    private Integer sex;
    private Integer age;
    private String edu;
    private String city;
    private String birthday;
    @Property(name = "cover_pic")
    private String coverPic;
    private String industry;
}
