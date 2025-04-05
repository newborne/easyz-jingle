package cn.easyz.server.service.v1.impl;

import com.github.tobato.fastdfs.domain.conn.FdfsWebServer;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import cn.easyz.common.model.dto.PageInfoDTO;
import cn.easyz.common.model.dto.VideoDTO;
import cn.easyz.common.model.enums.MsgEnum;
import cn.easyz.common.model.pojo.mongodb.Video;
import cn.easyz.common.model.pojo.mysql.ApUser;
import cn.easyz.common.model.pojo.mysql.ApUserInfo;
import cn.easyz.common.model.vo.ResponseResult;
import cn.easyz.common.util.RelativeDateFormat;
import cn.easyz.common.util.UserThreadLocal;
import cn.easyz.dubbo.api.v1.CommentApi;
import cn.easyz.dubbo.api.v1.VideoApi;
import cn.easyz.server.service.v1.ApUserInfoService;
import cn.easyz.server.service.v1.MqService;
import cn.easyz.server.service.v1.PicUploadService;
import cn.easyz.server.service.v1.VideoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The type Video service.
 */
@Service
public class VideoServiceImpl implements VideoService {
    @DubboReference(version = "1.0.0")
    private VideoApi videoApi;
    @Autowired
    private ApUserInfoService apUserInfoService;
    @Autowired
    private PicUploadService picUploadService;
    @Autowired
    private MqService mqService;
    /**
     * The Storage client.
     */
    @Autowired
    protected FastFileStorageClient storageClient;
    @Autowired
    private FdfsWebServer fdfsWebServer;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @DubboReference(version = "1.0.0")
    private CommentApi commentApi;
    @Override
    public ResponseResult saveVideo(String text, MultipartFile picFile, MultipartFile videoFile) throws IOException {
        ApUser user = UserThreadLocal.get();
        Video video = new Video();
        video.setUserId(Long.valueOf(user.getId()));
        video.setText(text);
        // String url =
        String url = null;
        video.setPicUrl(url);
        StorePath storePath = storageClient.uploadFile(videoFile.getInputStream(), videoFile.getSize(),
                StringUtils.substringAfter(videoFile.getOriginalFilename(), "."), null);
        video.setVideoUrl(fdfsWebServer.getWebServerUrl() + "/" + storePath.getFullPath());
        String publishId = this.videoApi.saveVideo(video);
        if (StringUtils.isNotEmpty(publishId)) {
            //发送消息
            this.mqService.sendMsg("video",
                    MsgEnum.SAVE,
                    publishId);
            return ResponseResult.ok();
        }
        return ResponseResult.fail();
    }
    @Override
    public ResponseResult queryFriendVideoList(Integer page, Integer size) {
        List<Video> videoList = this.videoApi.queryFriendVideoList(Long.valueOf(UserThreadLocal.get().getId()),
                page,
                size);
        List<VideoDTO> dtos = new ArrayList<>();
        for (Video video : videoList) {
            VideoDTO dto = this.fillValueToVideo(video);
            dtos.add(dto);
        }
        return ResponseResult.ok(new PageInfoDTO<>(0, page, size, dtos));
    }
    @Override
    public ResponseResult queryRecommendVideoList(Integer page, Integer size) {
        List<Video> videoList = this.videoApi.queryRecommendVideoList(Long.valueOf(UserThreadLocal.get().getId()),
                page,
                size);
        List<VideoDTO> dtos = new ArrayList<>();
        for (Video video : videoList) {
            VideoDTO dto = this.fillValueToVideo(video);
            dtos.add(dto);
        }
        return ResponseResult.ok(new PageInfoDTO<>(0, page, size, dtos));
    }
    @Override
    public ResponseResult queryUserVideoList(Long userId, Integer page, Integer size) {
        List<Video> videoList = this.videoApi.queryUserVideoList(userId, page, size);
        List<VideoDTO> dtos = new ArrayList<>();
        for (Video video : videoList) {
            VideoDTO dto = this.fillValueToVideo(video);
            dtos.add(dto);
        }
        return ResponseResult.ok(new PageInfoDTO<>(0, page, size, dtos));
    }
    @Override
    public ResponseResult queryVideoById(String id) {
        Video video = this.videoApi.queryVideoById(id);
        VideoDTO dto = this.fillValueToVideo(video);
        return ResponseResult.ok(dto);
    }
    // 填充值
    private VideoDTO fillValueToVideo(Video video) {
        VideoDTO dto = new VideoDTO();
        // 填充Video
        dto.setCreated(RelativeDateFormat.format(new Date(video.getCreated())));
        dto.setId(video.getId().toHexString());
        dto.setText(video.getText());
        dto.setPicUrl(video.getPicUrl());
        dto.setVideoUrl(video.getVideoUrl());
        dto.setUserId(video.getUserId());
        // 填充UserInfo
        ApUserInfo userInfo = this.apUserInfoService.queryUserInfoByUserId(dto.getUserId());
        dto.setLogo(userInfo.getLogo());
        dto.setNickName(userInfo.getNickName());
        dto.setSignature("");
        // 填充评论相关
        ApUser user = UserThreadLocal.get();
        String likeUserCommentKey = "COMMENT_LIKE_USER_" + user.getId() + "_" + dto.getId();
        // 是否点赞
        dto.setHasLiked(this.redisTemplate.hasKey(likeUserCommentKey) ? 1 : 0);
        String likeCommentKey = "COMMENT_LIKE_" + dto.getId();
        String value = this.redisTemplate.opsForValue().get(likeCommentKey);
        if (StringUtils.isNotEmpty(value)) {
            dto.setLikeCount(Integer.valueOf(value));
        } else {
            dto.setLikeCount(0);
        }
        String loveUserCommentKey = "COMMENT_LOVE_USER_" + user.getId() + "_" + dto.getId();
        // 文字评论
        Long commentCount = this.commentApi.queryCommentCount(dto.getId(), 2);
        dto.setCommentCount(commentCount == null ? 0 : commentCount.intValue());
        return dto;
    }
}
