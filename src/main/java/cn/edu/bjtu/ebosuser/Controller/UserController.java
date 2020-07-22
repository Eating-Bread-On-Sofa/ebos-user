package cn.edu.bjtu.ebosuser.Controller;


import cn.edu.bjtu.ebosuser.entity.User;
import cn.edu.bjtu.ebosuser.result.Result;
import cn.edu.bjtu.ebosuser.result.ResultFactory;
import cn.edu.bjtu.ebosuser.service.AdminUserRoleService;
import cn.edu.bjtu.ebosuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api")
@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    AdminUserRoleService adminUserRoleService;

    @GetMapping("/admin/user")
    public Result listUsers() {
        return ResultFactory.buildSuccessResult(userService.list());
    }

    @PutMapping("/admin/user/status")
    public Result updateUserStatus(@RequestBody @Valid User requestUser) {
        userService.updateUserStatus(requestUser);
        return ResultFactory.buildSuccessResult("用户状态更新成功");
    }

    @PutMapping("/admin/user/password")
    public Result resetPassword(@RequestBody @Valid User requestUser) {
        userService.resetPassword(requestUser);
        return ResultFactory.buildSuccessResult("重置密码成功");

    }

    @PutMapping("/admin/user")
    public Result editUser(@RequestBody @Valid User requestUser) {
        userService.editUser(requestUser);
        return ResultFactory.buildSuccessResult("修改用户信息成功");
    }

    @DeleteMapping("/admin/user/id/{id}")
    public Result deleteUser(@PathVariable String id) {
        userService.deleteById(id);
        return ResultFactory.buildSuccessResult("用户已删除");
    }

    @GetMapping("/user/info")
    public Result getCurrentUser() {
        return ResultFactory.buildSuccessResult(userService.getCurrentUserInfo());
    }

    @PutMapping("/user/info")
    public Result updateUserInfo(@RequestBody User user) {
        int state = userService.updateUserInfo(user);
        switch (state) {
            case 0:
                return ResultFactory.buildFailResult("用户名已被使用");
            case 1:
                return ResultFactory.buildSuccessResult("成功修改用户信息");
            case 2:
                return ResultFactory.buildFailResult("用户名不能为空");
        }
        return ResultFactory.buildFailResult("未知错误");
    }

}
