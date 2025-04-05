package cn.easyz.login.v1;

import cn.easyz.common.model.pojo.mysql.ApUser;
import cn.easyz.common.model.vo.ResponseResult;
import cn.easyz.login.service.LoginService;
import cn.easyz.login.service.impl.SmsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * The type Login controller.
 */
@RestController
@RequestMapping("api/v1/login")
@Slf4j
public class LoginController {
    //注入service
    @Autowired
    private LoginService loginService;
    @Autowired
    private SmsServiceImpl smsService;
    /**
     * Send check code response result.
     *
     * @param param the param
     * @return the response result
     */
    @PostMapping("send")
    public ResponseResult sendCheckCode(@RequestBody Map<String, String> param) {
        return smsService.sendCheckCode(param.get("mobile"));
    }
    /**
     * Login response result.
     *
     * @param param the param
     * @return the response result
     */
    @PostMapping("login")
    public ResponseResult login(@RequestBody Map<String, String> param) {
        return loginService.login(param.get("mobile"), param.get("verificationCode"));
    }
    /**
     * Query user by token ap user.
     *
     * @param token the token
     * @return the ap user
     */
    @GetMapping("{token}")
    public ApUser queryUserByToken(@PathVariable("token") String token) {
        return loginService.queryUserByToken(token);
    }
    /**
     * Save user info response result.
     *
     * @param param the param
     * @param token the token
     * @return the response result
     */
    @PostMapping("saveUserInfo")
    public ResponseResult saveUserInfo(@RequestBody Map<String, String> param,
                                       @RequestHeader("Authorization") String token) {
        return this.loginService.saveUserInfo(param, token);
    }
    /**
     * Save user logo response result.
     *
     * @param file  the file
     * @param token the token
     * @return the response result
     */
    @PostMapping("saveUserLogo")
    public ResponseResult saveUserLogo(@RequestParam("logo") MultipartFile file,
                                       @RequestHeader("Authorization") String token) {
        return this.loginService.saveUserLogo(file, token);
    }
    /**
     * Send to old mobile response result.
     *
     * @param token the token
     * @return the response result
     */
    @PostMapping("mobile/send")
    public ResponseResult sendToOldMobile(@RequestHeader("Authorization") String token) {
        return this.loginService.sendToOldMobile(token);
    }
    /**
     * Save new mobile response result.
     *
     * @param param the param
     * @param token the token
     * @return the response result
     */
    @PostMapping("mobile/new")
    public ResponseResult saveNewMobile(@RequestBody Map<String, String> param,
                                        @RequestHeader("Authorization") String token) {
        return this.loginService.updateNewMobile(token, param.get("mobile"));
    }
}
