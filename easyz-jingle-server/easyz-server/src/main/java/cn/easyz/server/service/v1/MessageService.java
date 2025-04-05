package cn.easyz.server.service.v1;

import cn.easyz.common.model.vo.ResponseResult;

/**
 * The interface Message service.
 */
public interface MessageService {
    /**
     * Query message comment list response result.
     *
     * @param commentType the comment type
     * @param page        the page
     * @param size        the size
     * @return the response result
     */
    ResponseResult queryMessageCommentList(Integer commentType, Integer page, Integer size);
    /**
     * Query message announcement list response result.
     *
     * @param page the page
     * @param size the size
     * @return the response result
     */
    ResponseResult queryMessageAnnouncementList(Integer page, Integer size);
}
