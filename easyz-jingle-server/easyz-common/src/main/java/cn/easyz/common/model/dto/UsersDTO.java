package cn.easyz.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Users dto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {
    private Long id;
    private String userId;
    private String logo;
    private String nickName;
    private String sex;
    private Integer age;
    private String city;
}
