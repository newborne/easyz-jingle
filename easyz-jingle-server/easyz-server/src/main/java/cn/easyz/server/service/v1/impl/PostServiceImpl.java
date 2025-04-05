package cn.easyz.server.service.v1.impl;

import cn.easyz.common.model.dto.PageInfoDTO;
import cn.easyz.common.model.dto.PostDTO;
import cn.easyz.common.model.enums.MsgEnum;
import cn.easyz.common.model.pojo.mongodb.Post;
import cn.easyz.common.model.pojo.mysql.ApUser;
import cn.easyz.common.model.pojo.mysql.ApUserInfo;
import cn.easyz.common.model.vo.ResponseResult;
import cn.easyz.common.util.RelativeDateFormat;
import cn.easyz.common.util.UserThreadLocal;
import cn.easyz.dubbo.api.v1.CommentApi;
import cn.easyz.dubbo.api.v1.PostApi;
import cn.easyz.dubbo.api.v1.UsersApi;
import cn.easyz.server.service.v1.ApUserInfoService;
import cn.easyz.server.service.v1.MqService;
import cn.easyz.server.service.v1.PicUploadService;
import cn.easyz.server.service.v1.PostService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * The type Post service.
 */
@Service
public class PostServiceImpl implements PostService {
    @DubboReference(version = "1.0.0")
    private PostApi postApi;
    @Autowired
    private PicUploadService picUploadService;
    @Autowired
    private MqService mqService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private ApUserInfoService userService;
    @DubboReference(version = "1.0.0")
    private CommentApi commentApi;
    @DubboReference(version = "1.0.0")
    private UsersApi usersApi;
    @Override
    public ResponseResult savePost(String text,
                                   String location,
                                   String longitude,
                                   String latitude,
                                   MultipartFile[] multipartFile) {
        ApUser user = UserThreadLocal.get();
        //创建新的发布对象
        Post post = new Post();
        post.setUserId(Long.valueOf(user.getId()));
        post.setText(text);
        post.setLongitude(longitude);
        post.setLatitude(latitude);
        post.setLocation(location);
        post.setSeeType(1);
        List<String> picUrls = new ArrayList<>();
        //处理上传的图片使用阿里云保存
        // for (MultipartFile file : multipartFile) {
        //     PicUploadResult picUploadResult = this.picUploadService.uploadPic(file);
        //     picUrls.add(picUploadResult.getName());
        // }
        post.setMedias(picUrls);
        String publishId = this.postApi.savePost(post);
        if (StringUtils.isNotEmpty(publishId)) {
            //发送消息
            this.mqService.sendMsg("post",
                    MsgEnum.SAVE,
                    publishId);
            return ResponseResult.ok();
        }
        return ResponseResult.fail();
    }
    @Override
    public ResponseResult queryFriendPostList(Integer page, Integer size) {
        List<Post> postList = this.postApi.queryFriendPostList(Long.valueOf(UserThreadLocal.get().getId()), page, size);
        List<PostDTO> dtos = new ArrayList<>();
        for (Post post : postList) {
            dtos.add(this.fillValueToPost(post));
        }
        return ResponseResult.ok(new PageInfoDTO<>(0, page, size, dtos));
    }
    @Override
    public ResponseResult queryRecommendPostList(Integer page, Integer size) {
        List<Post> postList = this.postApi.queryRecommendPostList(Long.valueOf(UserThreadLocal.get().getId()),
                page,
                size);
        List<PostDTO> dtos = new ArrayList<>();
        for (Post post : postList) {
            dtos.add(this.fillValueToPost(post));
        }
        return ResponseResult.ok(new PageInfoDTO<>(0, page, size, dtos));
    }
    @Override
    public ResponseResult queryUserPostList(Long userId, Integer page, Integer size) {
        List<Post> postList = this.postApi.queryUserPostList(userId, page, size);
        List<PostDTO> dtos = new ArrayList<>();
        for (Post post : postList) {
            ;
            dtos.add(this.fillValueToPost(post));
        }
        return ResponseResult.ok(new PageInfoDTO<>(0, page, size, dtos));
    }
    @Override
    public ResponseResult queryPostById(String id) {
        return ResponseResult.ok(this.fillValueToPost(this.postApi.queryPostById(id)));
    }

    /**
     * 生成文章摘要和标题。
     *
     * @param text 文章内容
     * @return 包含生成的摘要和标题的响应结果
     */
    @Override
    public ResponseResult<String> generateTitle(String text) {
        try {
            String generatedTitle = postApi.generateTitle(text);
            return ResponseResult.ok(generatedTitle); // 使用 ok 方法构建成功的响应结果
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail("Failed to generate title"); // 使用 fail 方法构建失败的响应结果
        }
    }

    // 填充
    private PostDTO fillValueToPost(Post post) {
        ApUser user = UserThreadLocal.get();
        // 填充参数
        PostDTO dto = new PostDTO();
        // 填充帖子内容
        dto.setId(post.getId().toHexString());
        dto.setMedias(post.getMedias().toArray(new String[]{}));
        dto.setText(post.getText());
        dto.setCreated(RelativeDateFormat.format(new Date(post.getCreated())));
        // 填充距离
        String distance = this.usersApi.queryDistance(Long.valueOf(user.getId()),
                post.getLongitude(),
                post.getLatitude());
        dto.setDistance(distance + "km");
        // 填充评论相关
        // 是否点赞
        String likeUserCommentKey = "COMMENT_LIKE_USER_" + user.getId() + "_" + dto.getId();
        dto.setHasLiked(this.redisTemplate.hasKey(likeUserCommentKey) ? 1 : 0);
        String likeCommentKey = "COMMENT_LIKE_" + dto.getId();
        String value = this.redisTemplate.opsForValue().get(likeCommentKey);
        if (StringUtils.isNotEmpty(value)) {
            dto.setLikeCount(Integer.valueOf(value));
        } else {
            dto.setLikeCount(0);
        }
        // 是否喜欢
        String loveUserCommentKey = "COMMENT_LOVE_USER_" + user.getId() + "_" + dto.getId();
        dto.setHasLoved(this.redisTemplate.hasKey(loveUserCommentKey) ? 1 : 0);
        String loveCommentKey = "COMMENT_LOVE_" + dto.getId();
        String loveValue = this.redisTemplate.opsForValue().get(loveCommentKey);
        if (StringUtils.isNotEmpty(loveValue)) {
            dto.setLoveCount(Integer.valueOf(loveValue));
        } else {
            dto.setLoveCount(0);
        }
        // 文字评论
        Long commentCount = this.commentApi.queryCommentCount(dto.getId(), 2);
        dto.setCommentCount(commentCount == null ? 0 : commentCount.intValue());
        // 填充UserInfo
        dto.setUserId(post.getUserId());
        ApUserInfo userInfo = this.userService.queryUserInfoByUserId(dto.getUserId());
        dto.setAge(userInfo.getAge());
        dto.setLogo(userInfo.getLogo());
        dto.setSex(userInfo.getSex().name().toLowerCase(Locale.ROOT));
        dto.setNickName(userInfo.getNickName());
        dto.setTags(StringUtils.split(userInfo.getTags(), ","));
        return dto;
    }
}
