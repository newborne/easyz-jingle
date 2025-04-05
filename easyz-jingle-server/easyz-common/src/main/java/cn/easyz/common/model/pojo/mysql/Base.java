package cn.easyz.common.model.pojo.mysql;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * The type Base pojo.
 */
@Data
@ApiModel
public abstract class Base {
    @TableField(value = "created", fill = FieldFill.INSERT) //MP自动填充
    private LocalDateTime created;
    @TableField(value = "updated", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updated;
}
