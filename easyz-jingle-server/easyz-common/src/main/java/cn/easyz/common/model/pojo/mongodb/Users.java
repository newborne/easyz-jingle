package cn.easyz.common.model.pojo.mongodb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * The type Users.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;
    private ObjectId id;
    private Long userId; //用户id
    private Long friendId; //好友id
    private Long created; //加好友时间
}
