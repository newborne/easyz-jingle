package cn.easyz.common.model.pojo.mongodb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * The type Time line.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "timeline")
public class TimeLine implements Serializable {
    private static final long serialVersionUID = 1L;
    private ObjectId id;
    private Long userId; // 发布用户id
    private ObjectId publishId; //发布id
    private Long created; //发布的时间
}
