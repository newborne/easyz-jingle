package cn.easyz.common.model.pojo.mongodb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The type Follow user.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "follow_user")
public class FollowUser implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private ObjectId id;
    private Long userId; //用户id，自己
    private Long friendId; //喜欢的用户id，对方
    private Long created; //创建时间
}
