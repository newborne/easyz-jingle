package cn.easyz.common.model.request;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Recommend user request.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class RecommendUserRequest {
    private Integer page = 1; //当前页数
    private Integer size = 10; //页尺寸
    private String sex; //性别 man woman
    private String lastLogin; //近期登陆时间
    private Integer age; //年龄
    private String city; //居住地
    private String education; //学历
}
