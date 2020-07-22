package cn.edu.bjtu.ebosuser.Controller;

import cn.edu.bjtu.ebosuser.entity.AdminMenu;
import cn.edu.bjtu.ebosuser.result.Result;
import cn.edu.bjtu.ebosuser.result.ResultFactory;
import cn.edu.bjtu.ebosuser.service.AdminMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class MenuController {
    @Autowired
    AdminMenuService adminMenuService;

    @GetMapping("/admin/menu")
    public Result menu() {
        return ResultFactory.buildSuccessResult(adminMenuService.getMenusByCurrentUser());
    }

    @GetMapping("/admin/role/menu")
    public Result listAllMenus() {
        return ResultFactory.buildSuccessResult(adminMenuService.getMenusByRoleId("5f0c9f77a3e34b54b4c2a019"));
    }

    @GetMapping("/admin/menu/all")
    public Result listDaoMenu() {
        return ResultFactory.buildSuccessResult(adminMenuService.getAllMenus());
    }

    @PostMapping("/admin/menu/all")
    public Result addMenu(@RequestBody AdminMenu adminMenu) {
        adminMenuService.addMenu(adminMenu);
        return ResultFactory.buildSuccessResult("成功添加菜单");
    }

    @GetMapping("/admin/menu/tree")
    public Result listMenuTree() {
        return ResultFactory.buildSuccessResult(adminMenuService.listMenuTree());
    }
}
