package cn.easyz.common.model.pojo.mongodb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The type Article listener msg.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "article_listener_msg")
public class ArticleListenerMsg {
    private ObjectId id;
    private Long userId;
    private Long articleRid;
    private Double rating;
    private Long created;
}
