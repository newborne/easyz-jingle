package cn.easyz.common.model.pojo.mongodb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The type Post listener msg.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "post_listener_msg")
public class PostListenerMsg {
    private ObjectId id;
    private Long userId;
    private Long postRid;
    private Double rating;
    private Long created;
}
