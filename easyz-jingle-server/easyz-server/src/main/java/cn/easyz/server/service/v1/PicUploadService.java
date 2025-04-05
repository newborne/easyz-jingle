package cn.easyz.server.service.v1;

import cn.easyz.common.model.vo.PicUploadResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * The interface Pic upload service.
 */
public interface PicUploadService {
    /**
     * Upload pic pic upload result.
     *
     * @param uploadFile the upload file
     * @return the pic upload result
     */
    PicUploadResult uploadPic(MultipartFile uploadFile);
}
