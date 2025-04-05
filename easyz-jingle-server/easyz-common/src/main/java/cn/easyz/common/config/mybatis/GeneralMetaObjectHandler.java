package cn.easyz.common.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * The type General meta object handler.
 */
@Component
public class GeneralMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Object created = getFieldValByName("created", metaObject);
        if (null == created) {
            //字段为空，可以进行填充
            setFieldValByName("created", LocalDateTime.now(), metaObject);
        }
        Object updated = getFieldValByName("updated", metaObject);
        if (null == updated) {
            //字段为空，可以进行填充
            setFieldValByName("updated", LocalDateTime.now(), metaObject);
        }
    }
    @Override
    public void updateFill(MetaObject metaObject) {
        //更新数据时，直接更新字段
        setFieldValByName("updated", LocalDateTime.now(), metaObject);
    }
}
