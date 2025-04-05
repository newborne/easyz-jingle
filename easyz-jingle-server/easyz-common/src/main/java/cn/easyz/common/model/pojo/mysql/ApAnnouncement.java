package cn.easyz.common.model.pojo.mysql;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The type Ap announcement.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class ApAnnouncement extends Base implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id")
    private Integer id;
    // 标题
    @ApiModelProperty(value = "标题")
    private String title;
    // 描述
    @ApiModelProperty(value = "描述")
    private String description;
}
