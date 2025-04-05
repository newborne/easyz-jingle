package cn.easyz.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The type Post dto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id; //动态id
    private Long userId; //用户id
    private String logo; //头像
    private String nickName; //昵称
    private String sex; //性别 man woman
    private Integer age; //年龄
    private String[] tags; //标签
    private String text; //文字动态
    private String[] medias; //图片动态
    private String distance; //距离
    private String created; //发布时间 如: 10分钟前
    private Integer likeCount; //点赞数
    private Integer commentCount; //评论数
    private Integer loveCount; //喜欢数
    private Integer hasLiked; //是否点赞（1是，0否）
    private Integer hasLoved; //是否喜欢（1是，0否）
}
