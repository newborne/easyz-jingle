package cn.easyz.common.model.pojo.mongodb;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The type Log.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "log")
@ApiModel
public class Log {
    private Long userId;
    private Long created;
    private String url;
    private String annotation;
}
