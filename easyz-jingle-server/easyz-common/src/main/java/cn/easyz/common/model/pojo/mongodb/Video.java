package cn.easyz.common.model.pojo.mongodb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * The type Video.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "video")
public class Video implements Serializable {
    private static final long serialVersionUID = 1L;
    private ObjectId id; //主键id
    private Long videoRid;//Long类型的id，用于推荐引擎使用
    private Long userId;
    private String text; //文字
    private String picUrl; //视频封面文件
    private String videoUrl; //视频文件
    private Long created; //创建时间
    private String longitude; //经度
    private String latitude; //纬度
    private String location; //位置名称
    private Integer seeType; // 谁可以看，1-公开，2-私密，3-部分可见，4-不给谁看
    private List<Long> seeList; //部分可见的列表
    private List<Long> notSeeList; //不给谁看的列表
}
