package cn.easyz.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The type Follow count dto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowCountDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long mutualFollowCount; //互相关注
    private Long followCount; //关注
    private Long fanCount; //粉丝
}
