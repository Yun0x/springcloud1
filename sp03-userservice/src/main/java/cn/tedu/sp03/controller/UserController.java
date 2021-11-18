package cn.tedu.sp03.controller;
import cn.tedu.sp01.entity.User;
import cn.tedu.sp01.service.UserService;
import cn.tedu.web.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    // 获取用户
    @GetMapping("/{userId}")
    public JsonResult<User> getUser(
            @PathVariable Integer userId) {
        User u = userService.getUser(userId);
        return JsonResult.ok().data(u);
    }
    // 增加积分 http://xxxx/8/score?score=1000
    @GetMapping("/{userId}/score")
    public JsonResult<?> addScore(
            @PathVariable Integer userId, Integer score) {
        userService.addScore(userId, score);
        return JsonResult.ok().msg("增加积分成功");
    }

    @GetMapping("/favicon.ico")
    public void ico() {
    }
}
