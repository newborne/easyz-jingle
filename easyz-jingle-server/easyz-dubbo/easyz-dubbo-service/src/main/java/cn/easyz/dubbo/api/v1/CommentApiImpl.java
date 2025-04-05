package cn.easyz.dubbo.api.v1;

import com.mongodb.client.result.DeleteResult;
import cn.easyz.common.model.pojo.mongodb.Comment;
import cn.easyz.common.model.pojo.mongodb.Post;
import cn.easyz.common.model.pojo.mongodb.Video;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * The type Comment api.
 */
@DubboService(version = "1.0.0")
@Slf4j
public class CommentApiImpl implements CommentApi {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public Boolean saveComment(Long userId, String publishId, Integer type, String text) {
        Comment comment = new Comment();
        comment.setId(ObjectId.get());
        comment.setPublishId(new ObjectId(publishId));
        comment.setText(text);
        comment.setUserId(userId);
        comment.setCommentType(type);
        comment.setIsParent(true);
        comment.setCreated(System.currentTimeMillis());
        //设置发布人的id
        Post post = this.mongoTemplate.findById(comment.getPublishId(), Post.class);
        if (post != null) {
            comment.setPublishUserId(post.getUserId());
        } else {
            Video video = this.mongoTemplate.findById(comment.getPublishId(), Video.class);
            if (video != null) {
                comment.setPublishUserId(video.getUserId());
            }
        }
        this.mongoTemplate.save(comment);
        return true;
    }
    @Override
    public Boolean removeComment(Long userId, String publishId, Integer commentType) {
        Query query = Query.query(Criteria.where("userId").is(userId)
                .and("publishId").is(new ObjectId(publishId))
                .and("commentType").is(commentType));
        DeleteResult remove = this.mongoTemplate.remove(query, Comment.class);
        return remove.getDeletedCount() > 0;
    }
    @Override
    public Long queryCommentCount(String publishId, Integer type) {
        Query query = Query.query(Criteria.where("publishId").is(new ObjectId(publishId)).and("commentType").is(type));
        return this.mongoTemplate.count(query, Comment.class);
    }
    @Override
    public List<Comment> queryTextCommentList(String publishId, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Order.desc("created")));
        Query query = Query.query(Criteria.where("publishId").is(new ObjectId(publishId)).and("commentType").is(3))
                .with(pageRequest);
        return this.mongoTemplate.find(query, Comment.class);
    }
    @Override
    public List<Comment> queryCommentListByPublishUserId(Long publishUserId, Integer type, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Order.desc("created")));
        Query query = Query.query(Criteria.where("publishUserId").is(publishUserId).and("commentType").is(type))
                .with(pageRequest);
        return this.mongoTemplate.find(query, Comment.class);
    }
}
