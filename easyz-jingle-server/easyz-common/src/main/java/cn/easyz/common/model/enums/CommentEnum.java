package cn.easyz.common.model.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import io.swagger.annotations.ApiModel;

/**
 * The enum Comment enum.
 */
@ApiModel
public enum CommentEnum implements IEnum<Integer> {
    /**
     * Like comment enum.
     */
    LIKE(1, "点赞"),
    /**
     * Comment comment enum.
     */
    COMMENT(3, "评论"),
    /**
     * Love comment enum.
     */
    LOVE(4, "喜欢");
    private final int type;
    private final String desc;
    CommentEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
    @Override
    public Integer getValue() {
        return this.type;
    }
    @Override
    public String toString() {
        return this.desc;
    }
}

