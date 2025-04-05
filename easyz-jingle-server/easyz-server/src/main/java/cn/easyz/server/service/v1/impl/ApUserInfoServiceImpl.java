package cn.easyz.server.service.v1.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.easyz.common.model.enums.SexEnum;
import cn.easyz.common.model.mapper.ApUserInfoMapper;
import cn.easyz.common.model.pojo.mysql.ApUser;
import cn.easyz.common.model.pojo.mysql.ApUserInfo;
import cn.easyz.common.util.UserThreadLocal;
import cn.easyz.server.service.v1.ApUserInfoService;
import cn.easyz.server.service.v1.FaceEngineService;
import cn.easyz.server.service.v1.PicUploadService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * The type Ap user info service.
 */
@Service
public class ApUserInfoServiceImpl implements ApUserInfoService {
    @Resource
    private ApUserInfoMapper userInfoMapper;
    @Autowired
    private FaceEngineService faceEngineService;
    @Autowired
    private PicUploadService picUploadService;
    @Override
    public Boolean saveUserInfo(Map<String, String> param) {
        ApUser user = UserThreadLocal.get();
        ApUserInfo userInfo = new ApUserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setSex(StringUtils.equalsIgnoreCase(param.get("sex"), "man") ? SexEnum.MAN : SexEnum.WOMAN);
        userInfo.setNickName(param.get("nickName"));
        userInfo.setBirthday(param.get("birthday"));
        userInfo.setCity(param.get("city"));
        userInfo.setUserId(user.getId());
        return this.userInfoMapper.insert(userInfo) == 1;
    }
    @Override
    public Boolean saveUserLogo(MultipartFile file) {
        ApUser user = UserThreadLocal.get();
        //如果不是人像就返回false
        try {
            Boolean b = faceEngineService.checkIsPortrait(file.getBytes());
            if (!b) {
                return false;
            }
        } catch (IOException e) {
            return false;
        }
        //是人像,上传阿里云
//        PicUploadResult result = picUploadService.uploadPic(file);
//        if (!StringUtils.equalsIgnoreCase(result.getStatus(), "done")) {
//            return false;
//        }
        ApUserInfo userInfo = new ApUserInfo();
//        userInfo.setLogo(result.getName());
        userInfo.setLogo("https://z3.ax1x.com/2021/05/26/2SOHRU.jpg");
        //更新图片地址到userinfo
        LambdaQueryWrapper<ApUserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ApUserInfo::getUserId, user.getId());
        return userInfoMapper.update(userInfo, queryWrapper) == 1;
    }
    @Override
    public ApUserInfo queryUserInfoByUserId(Long userId) {
        LambdaQueryWrapper<ApUserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ApUserInfo::getUserId, userId);
        return userInfoMapper.selectOne(queryWrapper);
    }
    @Override
    public List<ApUserInfo> queryUserInfoList(QueryWrapper queryWrapper) {
        return this.userInfoMapper.selectList(queryWrapper);
    }
    @Override
    public Boolean updateUserInfo(ApUserInfo userInfo) {
        QueryWrapper<ApUserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userInfo.getUserId());
        return this.userInfoMapper.update(userInfo, queryWrapper) > 0;
    }
}
