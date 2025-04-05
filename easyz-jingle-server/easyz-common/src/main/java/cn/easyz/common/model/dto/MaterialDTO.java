package cn.easyz.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The type Material dto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String materialRid;
    private String[] tags;
    private String text;
    private Integer loveCount;
    private Integer hasLoved;
}
