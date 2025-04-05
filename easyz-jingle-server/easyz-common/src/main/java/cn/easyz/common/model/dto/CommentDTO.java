package cn.easyz.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The type Comment dto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id; //评论id
    private String logo; //头像
    private String nickName; //昵称
    private String text; //评论
    private String created; //评论时间: 08:27
    private Integer likeCount; //点赞数
    private Integer hasLiked; //是否点赞（1是，0否）
}
