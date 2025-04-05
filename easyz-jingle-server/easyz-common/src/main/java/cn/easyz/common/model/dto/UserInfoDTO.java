package cn.easyz.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type User info dto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    private Long id;
    private String logo; //头像
    private String nickName; //昵称
    private String birthday; //生日 2019-09-11
    private String age; //年龄
    private String sex; //性别 man woman
    private String city; //城市
    private String edu; //学历
    private String industry; //行业
}
