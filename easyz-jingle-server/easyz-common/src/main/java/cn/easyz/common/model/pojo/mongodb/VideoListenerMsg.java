package cn.easyz.common.model.pojo.mongodb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The type Video listener msg.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "video_listener_msg")
public class VideoListenerMsg {
    private ObjectId id;
    private Long userId;
    private Long videoRid;
    private Double rating;
    private Long created;
}
