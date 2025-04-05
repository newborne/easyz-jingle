package cn.easyz.server.controller.v1;

import cn.easyz.common.model.request.RecommendUserRequest;
import cn.easyz.common.model.vo.ResponseResult;
import cn.easyz.server.annotation.Cache;
import cn.easyz.server.service.v1.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * The type Users controller.
 */
@RestController
@RequestMapping("api/v1/users")
public class UsersController {
    @Autowired
    private UsersService usersService;
    /**
     * Add users response result.
     *
     * @param friendId the friend id
     * @return the response result
     */
    @GetMapping("add/{friendId}")
    public ResponseResult addUsers(@PathVariable("friendId") Long friendId) {
        return this.usersService.addUsers(friendId);
    }
    /**
     * Query users list response result.
     *
     * @param page    the page
     * @param size    the size
     * @param keyword the keyword
     * @return the response result
     */
    @GetMapping("list")
    public ResponseResult queryUsersList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size,
                                         @RequestParam(value = "keyword", required = false) String keyword) {
        return this.usersService.queryUsersList(page, size, keyword);
    }
    /**
     * User response result.
     *
     * @return the response result
     */
    @GetMapping("huanxin")
    public ResponseResult user() {
        return this.usersService.user();
    }
    /**
     * Register response result.
     *
     * @param userId the user id
     * @return the response result
     */
    @PostMapping("huanxin/register/{id}")
    public ResponseResult register(@PathVariable("id") Long userId) {
        return this.usersService.register(userId);
    }
    /**
     * Send msg response result.
     *
     * @param target the target
     * @param msg    the msg
     * @param type   the type
     * @return the response result
     */
    @PostMapping("huanxin/messages")
    public ResponseResult sendMsg(@RequestParam("target") String target,
                                  @RequestParam("msg") String msg,
                                  @RequestParam(value = "type", defaultValue = "txt") String type) {
        return this.usersService.sendMsg(target, msg, type);
    }
    /**
     * Save user info response result.
     *
     * @param param the param
     * @return the response result
     */
    @PostMapping("saveUserInfo")
    public ResponseResult saveUserInfo(@RequestBody Map<String, String> param) {
        return this.usersService.saveUserInfo(param);
    }
    /**
     * Save user logo response result.
     *
     * @param file the file
     * @return the response result
     */
    @PostMapping("saveUserLogo")
    public ResponseResult saveUserLogo(@RequestParam("logo") MultipartFile file) {
        return this.usersService.saveUserLogo(file);
    }
    /**
     * Query user info response result.
     *
     * @param userId the user id
     * @return the response result
     */
    @GetMapping("queryUserInfo/{userId}")
    public ResponseResult queryUserInfo(@PathVariable("userId") Long userId) {
        return this.usersService.queryUserInfo(userId);
    }
    /**
     * Update user info response result.
     *
     * @param param the param
     * @return the response result
     */
    @PostMapping("updateUserInfo")
    public ResponseResult updateUserInfo(@RequestBody Map<String, String> param) {
        return this.usersService.updateUserInfo(param);
    }
    /**
     * Query follow counts response result.
     *
     * @return the response result
     */
    @GetMapping("follow/counts")
    public ResponseResult queryFollowCounts() {
        return this.usersService.queryFollowCounts();
    }
    /**
     * Query follow list response result.
     *
     * @param type the type
     * @param page the page
     * @param size the size
     * @return the response result
     */
    @GetMapping("follow/list/{type}")
    public ResponseResult queryFollowList(@PathVariable("type") String type,
                                          @RequestParam(value = "page", defaultValue = "1") Integer page,
                                          @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return this.usersService.queryFollowList(type, page, size);
    }
    /**
     * Follow response result.
     *
     * @param friendId the friend id
     * @return the response result
     */
    @GetMapping("follow/{friendId}")
    public ResponseResult follow(@PathVariable("friendId") Long friendId) {
        return this.usersService.follow(friendId);
    }
    /**
     * Un follow response result.
     *
     * @param friendId the friend id
     * @return the response result
     */
    @GetMapping("unfollow/{friendId}")
    public ResponseResult unFollow(@PathVariable("friendId") Long friendId) {
        return this.usersService.unFollow(friendId);
    }
    /**
     * Query today best response result.
     *
     * @return the response result
     */
    @Cache
    @GetMapping("todayBest")
    public ResponseResult queryTodayBest() {
        return this.usersService.queryTodayBest();
    }
    /**
     * Query recommend user response result.
     *
     * @param friendId the friend id
     * @return the response result
     */
    @Cache
    @GetMapping("{id}")
    public ResponseResult queryRecommendUser(@PathVariable("id") Long friendId) {
        return this.usersService.queryRecommendUser(friendId);
    }
    /**
     * Query recommend user list response result.
     *
     * @param param the param
     * @return the response result
     */
    @Cache
    @GetMapping("recommendUserList")
    public ResponseResult queryRecommendUserList(RecommendUserRequest param) {
        return this.usersService.queryRecommendUserList(param);
    }
    /**
     * Update location response result.
     *
     * @param param the param
     * @return the response result
     */
    @PostMapping("updateLocation")
    public ResponseResult updateLocation(@RequestBody Map<String, Object> param) {
        return this.usersService.updateLocation(param);
    }
    /**
     * Query near user response result.
     *
     * @param sex      the sex
     * @param distance the distance
     * @return the response result
     */
    @GetMapping("near")
    public ResponseResult queryNearUser(@RequestParam(value = "sex", required = false) String sex,
                                        @RequestParam(value = "distance", defaultValue = "5000") String distance) {
        return this.usersService.queryNearUser(sex, distance);
    }
}

