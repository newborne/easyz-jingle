package cn.easyz.common.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * The type Page info dto.
 *
 * @param <T> the type parameter
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class PageInfoDTO<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer total = 0;//总记录数
    private Integer size = 0;//页大小
    private Integer page = 0;//当前页码
    private List<T> records = Collections.emptyList();
}
