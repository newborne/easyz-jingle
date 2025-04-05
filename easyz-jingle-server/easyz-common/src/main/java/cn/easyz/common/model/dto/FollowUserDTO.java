package cn.easyz.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The type Follow user dto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowUserDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String logo;
    private String nickName;
    private String sex;
    private Integer age;
    private String city;
    private String edu;
    private Double similarity; //匹配度
}
