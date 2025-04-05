package cn.easyz.common.model.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import io.swagger.annotations.ApiModel;

/**
 * The enum Msg enum.
 */
@ApiModel
public enum MsgEnum implements IEnum<Integer> {
    /**
     * Save msg enum.
     */
    SAVE(0, "发布"),
    /**
     * Like msg enum.
     */
    LIKE(1, "点赞"),
    /**
     * Dislike msg enum.
     */
    DISLIKE(2, "取消点赞"),
    /**
     * Comment msg enum.
     */
    COMMENT(3, "评论"),
    /**
     * Love msg enum.
     */
    LOVE(4, "喜欢"),
    /**
     * Unlove msg enum.
     */
    UNLOVE(5, "取消喜欢"),
    /**
     * Query msg enum.
     */
    QUERY(6, "浏览");
    private final int type;
    private final String desc;
    MsgEnum(int type, String desc) {
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
