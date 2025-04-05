package cn.easyz.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The type Video dto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id; //动态id
    private Long userId; //用户id
    private String logo; //头像
    private String nickName; //昵称
    private String picUrl; //封面
    private String text; //文字介绍
    private String videoUrl; //视频URL
    private String signature; //签名
    private String created; //发布时间 如: 10分钟前
    private Integer likeCount; //点赞数
    private Integer commentCount; //评论数
    private Integer hasLiked; //是否点赞（1是，0否）
}
