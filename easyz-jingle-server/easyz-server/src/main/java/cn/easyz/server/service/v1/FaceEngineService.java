package cn.easyz.server.service.v1;

import com.arcsoft.face.toolkit.ImageInfo;

import java.io.File;

/**
 * The interface Face engine service.
 */
public interface FaceEngineService {
    /**
     * Check is portrait boolean.
     *
     * @param imageInfo the image info
     * @return the boolean
     */
    Boolean checkIsPortrait(ImageInfo imageInfo);
    /**
     * Check is portrait boolean.
     *
     * @param imageData the image data
     * @return the boolean
     */
    Boolean checkIsPortrait(byte[] imageData);
    /**
     * Check is portrait boolean.
     *
     * @param file the file
     * @return the boolean
     */
    Boolean checkIsPortrait(File file);
}

