package cn.easyz.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Near user dto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NearUserDTO {
    private Long userId;
    private String logo;
    private String nickName;
}
