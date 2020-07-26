package cn.edu.bjtu.ebosuser.Controller;

import cn.edu.bjtu.ebosuser.entity.*;
import cn.edu.bjtu.ebosuser.result.Result;
import cn.edu.bjtu.ebosuser.result.ResultFactory;
import cn.edu.bjtu.ebosuser.service.AdminInitService;
import cn.edu.bjtu.ebosuser.service.AdminUserRoleService;
import cn.edu.bjtu.ebosuser.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.List;


@RequestMapping("/api")
@RestController
public class LoginController {

    @Autowired
    UserService userService;
    @Autowired
    AdminInitService adminInitService;
    @Autowired
    AdminUserRoleService adminUserRoleService;

    @PostMapping("/login")
    public Result login(@RequestBody User requestUser) {
        // 对 html 标签进行转义，防止 XSS 攻击
        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, requestUser.getPassword());
        usernamePasswordToken.setRememberMe(true);
        try {
            subject.login(usernamePasswordToken);
            User user = userService.getByUserName(username);
            if (!user.isEnabled()) {
                return ResultFactory.buildFailResult("用户已被禁用");
            }
            return ResultFactory.buildSuccessResult(username);
        } catch (IncorrectCredentialsException e) {
            return ResultFactory.buildFailResult("账号密码错误");
        } catch (UnknownAccountException e) {
            return ResultFactory.buildFailResult("账号不存在");
        }
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        int status = userService.register(user);
        switch (status) {
            case 0:
                return ResultFactory.buildFailResult("用户名和密码不能为空");
            case 1:
                return ResultFactory.buildSuccessResult("注册成功");
            case 2:
                return ResultFactory.buildFailResult("用户已存在");
        }
        return ResultFactory.buildFailResult("未知错误");

    }

    @GetMapping("/logout")
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResultFactory.buildSuccessResult("成功登出");
    }

    @GetMapping("/authentication")
    public String authentication() {
        return "身份认证成功";
    }

    @PostMapping("/add/role")
    public Result addRole(@RequestBody AdminRole adminRole) {
        int roleStatus = adminInitService.addRole(adminRole);
        switch (roleStatus) {
            case 0:
                return ResultFactory.buildFailResult("角色名不能为空");
            case 1:
                return ResultFactory.buildSuccessResult("添加角色成功");
//            case 2:
//                return ResultFactory.buildFailResult("角色已存在");
        }
        return ResultFactory.buildFailResult("未知错误");
    }

    @PostMapping("add/menu")
    public Result addMenu(@RequestBody AdminMenu adminMenu) {
        int menuStatus = adminInitService.addMenu(adminMenu);
        switch (menuStatus) {
            case 1:
                return ResultFactory.buildSuccessResult("添加菜单成功");
        }
        return ResultFactory.buildFailResult("未知错误");
    }

    @PostMapping("/add/roleMenu")
    public Result addRoleMenu(@RequestBody AdminRoleMenu adminRoleMenu) {
        int roleMenuStatus = adminInitService.addRoleMenu(adminRoleMenu);
        switch (roleMenuStatus) {
            case 1:
                return ResultFactory.buildSuccessResult("绑定角色菜单成功");
        }
        return ResultFactory.buildFailResult("未知错误");
    }

    @PostMapping("/add/userRole")
    public Result addUserRole(@RequestBody AdminUserRole adminUserRole) {
        int userRoleStatus = adminInitService.addUserRole(adminUserRole);
        switch (userRoleStatus) {
            case 1:
                return ResultFactory.buildSuccessResult("绑定用户角色成功");
        }
        return ResultFactory.buildFailResult("未知错误");
    }

    @PostMapping("/user/detail/id")
    public Result userId(@RequestBody String id) {
        User user = userService.getByUserId(id);
        String name = user.getName();

        return ResultFactory.buildSuccessResult(name);
    }

    @PostMapping("/user/role")
    public Result userRole(@RequestBody String uid) {
        List<AdminUserRole> adminUserRoles = adminUserRoleService.listAllByUid(uid);
        return ResultFactory.buildSuccessResult(adminUserRoles);
    }

    @PostMapping("/add/perm")
    public Result addPerm(@RequestBody AdminPermission adminPermission) {
        int addPermStatus = adminInitService.addPermission(adminPermission);
        switch (addPermStatus) {
            case 1:
                return ResultFactory.buildSuccessResult("添加权限成功");
        }
        return ResultFactory.buildSuccessResult("未知错误");
    }

    @PostMapping("/role/permission")
    public Result rolePermission(@RequestBody AdminRolePermission adminRolePermission) {
        int rolePermissionStatus = adminInitService.addRolePerms(adminRolePermission);
        switch (rolePermissionStatus) {
            case 1:
                return ResultFactory.buildSuccessResult("用户权限绑定成功");
        }
        return ResultFactory.buildFailResult("未知错误");
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }



}
