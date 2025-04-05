package cn.easyz.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Comment message dto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentMessageDTO {
    private String id;
    private String logo;
    private String nickName;
    private String created;
}
