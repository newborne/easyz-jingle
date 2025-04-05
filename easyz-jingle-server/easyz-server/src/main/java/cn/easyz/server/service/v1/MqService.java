package cn.easyz.server.service.v1;

import cn.easyz.common.model.enums.MsgEnum;

/**
 * The interface Mq service.
 */
public interface MqService {
    /**
     * Send msg boolean.
     *
     * @param destination the destination
     * @param type        the type
     * @param publishId   the publish id
     * @return the boolean
     */
    Boolean sendMsg(String destination, MsgEnum type, String publishId);
}
