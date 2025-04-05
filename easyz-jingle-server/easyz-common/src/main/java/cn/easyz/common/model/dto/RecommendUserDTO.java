package cn.easyz.common.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The type Recommend user dto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class RecommendUserDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long friendId;
    private String logo;
    private String nickName;
    private String sex; //性别 man woman
    private Integer age;
    private String[] tags;
    private Double similarity; //相似度
}


