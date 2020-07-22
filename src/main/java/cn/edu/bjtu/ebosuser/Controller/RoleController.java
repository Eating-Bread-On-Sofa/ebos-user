package cn.edu.bjtu.ebosuser.Controller;

import cn.edu.bjtu.ebosuser.entity.AdminPermission;
import cn.edu.bjtu.ebosuser.entity.AdminRole;
import cn.edu.bjtu.ebosuser.result.Result;
import cn.edu.bjtu.ebosuser.result.ResultFactory;
import cn.edu.bjtu.ebosuser.service.AdminPermissionService;
import cn.edu.bjtu.ebosuser.service.AdminRoleMenuService;
import cn.edu.bjtu.ebosuser.service.AdminRolePermissionService;
import cn.edu.bjtu.ebosuser.service.AdminRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/admin")
@RestController
public class RoleController {
    @Autowired
    AdminRoleService adminRoleService;
    @Autowired
    AdminPermissionService adminPermissionService;
    @Autowired
    AdminRolePermissionService adminRolePermissionService;
    @Autowired
    AdminRoleMenuService adminRoleMenuService;

    @GetMapping("/role")
    public Result listRoles() {
        return ResultFactory.buildSuccessResult(adminRoleService.listWithPermsAndMenus());
    }

    @PutMapping("/role/status")
    public Result updateRoleStatus(@RequestBody AdminRole requestRole) {
        AdminRole adminRole = adminRoleService.updateRoleStatus(requestRole);
        String message = "用户" + adminRole.getNameZh() + "状态更新成功";
        return ResultFactory.buildSuccessResult(message);
    }

    @PutMapping("/role")
    public Result editRole(@RequestBody AdminRole requestRole) {
        adminRoleService.updateRole(requestRole);
        return ResultFactory.buildSuccessResult("修改角色信息成功");
    }

    @PostMapping("/role")
    public Result addRole(@RequestBody AdminRole requestRole) {
        adminRoleService.editRole(requestRole);
        return ResultFactory.buildSuccessResult("添加角色成功");
    }

    @GetMapping("/role/perm")
    public Result listPerms() {
        return ResultFactory.buildSuccessResult(adminPermissionService.list());
    }

    @DeleteMapping("/role/id/{rid}")
    public Result deleteRole(@PathVariable String rid) {
        adminRoleService.deleteRole(rid);
        return ResultFactory.buildSuccessResult("成功删除角色信息");
    }

//    @PutMapping("/role/menu")
//    public Result updateRoleMenu(@RequestParam String rid, @RequestBody List<String> menusIds) {
//        adminRoleMenuService.updateRoleMenu(rid, menusIds);
//        return ResultFactory.buildSuccessResult("更新成功");
//    }
}
