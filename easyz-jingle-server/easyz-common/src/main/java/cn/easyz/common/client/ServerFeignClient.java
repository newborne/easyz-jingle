package cn.easyz.common.client;

import cn.easyz.common.model.vo.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * The interface Server feign client.
 */
@FeignClient(value = "easyz-server")
@Repository
public interface ServerFeignClient {
    /**
     * Save user logo response result.
     *
     * @param file  the file
     * @param token the token
     * @return the response result
     */
    @PostMapping(value = "api/v1/users/saveUserLogo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseResult saveUserLogo(@RequestPart("logo") MultipartFile file, @RequestHeader("Authorization") String token);
    /**
     * Save user info response result.
     *
     * @param param the param
     * @param token the token
     * @return the response result
     */
    @GetMapping("api/v1/users/saveUserInfo")
    ResponseResult saveUserInfo(@RequestBody Map<String, String> param, @RequestHeader("Authorization") String token);
    /**
     * Register response result.
     *
     * @param userId the user id
     * @param token  the token
     * @return the response result
     */
    @PostMapping("api/v1/users/huanxin/register/{id}")
    ResponseResult register(@PathVariable("id") Long userId, @RequestHeader("Authorization") String token);
}
