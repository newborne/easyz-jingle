package cn.easyz.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The type Article dto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String articleRid;
    private String title;
    private String[] tags;
    private String text;
    private Integer likeCount;
    private Integer hasLiked;
}
