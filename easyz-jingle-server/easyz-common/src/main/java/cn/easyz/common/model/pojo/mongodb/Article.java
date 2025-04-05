package cn.easyz.common.model.pojo.mongodb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * The type Article.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "article")
public class Article implements Serializable {
    private static final long serialVersionUID = 1L;
    private ObjectId id; //主键id
    private Long articleRid;//Long类型的id，用于推荐引擎使用
    private String title;
    private Integer articleType;
    private String[] tags;
    private String text; //文字
}

