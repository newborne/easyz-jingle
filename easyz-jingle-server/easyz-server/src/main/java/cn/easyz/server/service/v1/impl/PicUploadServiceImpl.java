package cn.easyz.server.service.v1.impl;

import com.aliyun.oss.OSSClient;
import cn.easyz.common.config.aliyun.AliyunConfig;
import cn.easyz.common.model.vo.PicUploadResult;
import cn.easyz.server.service.v1.PicUploadService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

/**
 * The type Pic upload service.
 */
@Service
public class PicUploadServiceImpl implements PicUploadService {
    // 允许上传的格式
    private static final String[] IMAGE_TYPE = new String[]{
            ".bmp", ".jpg",
            ".jpeg", ".gif", ".png"
    };
    @Autowired
    private OSSClient ossClient;
    @Autowired
    private AliyunConfig aliyunConfig;
    @Override
    public PicUploadResult uploadPic(MultipartFile uploadFile) {
        PicUploadResult fileUploadResult = new PicUploadResult();
        //图片做校验，对后缀名
        boolean isLegal = false;
        for (String type : IMAGE_TYPE) {
            if (StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(),
                    type)) {
                isLegal = true;
                break;
            }
        }
        if (!isLegal) {
            fileUploadResult.setStatus("error");
            return fileUploadResult;
        }
        // 文件新路径
        String fileName = uploadFile.getOriginalFilename();
        String filePath = getFilePath(fileName);
        // 上传到阿里云
        try {
            // 目录结构：images/2021/04/28/16195805772213366.png
            ossClient.putObject(aliyunConfig.getBucketName(), filePath, new
                    ByteArrayInputStream(uploadFile.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            //上传失败
            fileUploadResult.setStatus("error");
            return fileUploadResult;
        }
        // 上传成功
        fileUploadResult.setStatus("done");
        fileUploadResult.setName(this.aliyunConfig.getUrlPrefix() + filePath);
        fileUploadResult.setUid(String.valueOf(System.currentTimeMillis()));
        return fileUploadResult;
    }
    private String getFilePath(String sourceFileName) {
        DateTime dateTime = new DateTime();
        return "images/" + dateTime.toString("yyyy")
                + "/" + dateTime.toString("MM") + "/"
                + dateTime.toString("dd") + "/" + System.currentTimeMillis() +
                RandomUtils.nextInt(100, 9999) + "." +
                StringUtils.substringAfterLast(sourceFileName, ".");
    }
}
