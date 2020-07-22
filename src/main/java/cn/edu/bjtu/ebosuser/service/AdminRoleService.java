package cn.edu.bjtu.ebosuser.service;

import cn.edu.bjtu.ebosuser.dao.AdminRoleRepo;
import cn.edu.bjtu.ebosuser.entity.AdminMenu;
import cn.edu.bjtu.ebosuser.entity.AdminPermission;
import cn.edu.bjtu.ebosuser.entity.AdminRole;
import cn.edu.bjtu.ebosuser.entity.AdminUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminRoleService {
    @Autowired
    AdminRoleRepo adminRoleRepo;
    @Autowired
    UserService userService;
    @Autowired
    AdminUserRoleService adminUserRoleService;
    @Autowired
    AdminPermissionService adminPermissionService;
    @Autowired
    AdminRolePermissionService adminRolePermissionService;
    @Autowired
    AdminMenuService adminMenuService;
    @Autowired
    AdminRoleMenuService adminRoleMenuService;

    public List<AdminRole> listWithPermsAndMenus() {
        List<AdminRole> roles = adminRoleRepo.findAll();
        List<AdminPermission> perms;
        List<AdminMenu> menus;
        for (AdminRole role : roles) {
            perms = adminPermissionService.listPermsByRoleId(role.getId());
            menus = adminMenuService.getMenusByRoleId(role.getId());
            role.setPerms(perms);
            role.setMenus(menus);
        }
        return roles;
    }

    public List<AdminRole> findAll() {
        return adminRoleRepo.findAll();
    }

    public AdminRole findByRid(String rid) {return adminRoleRepo.findAdminRoleById(rid);}

    @Transactional
    public void updateRole(AdminRole adminRole) {
        adminRoleRepo.save(adminRole);
        adminRolePermissionService.savePermChanges(adminRole.getId(), adminRole.getPermIds());
        adminRoleMenuService.updateRoleMenu(adminRole.getId(), adminRole.getMenuIds());
    }
    public List<AdminRole> listRolesByUser(String username) {
        String uid = userService.getByUserName(username).getId();
        List<String> rids = adminUserRoleService.listAllByUid(uid)
                .stream().map(AdminUserRole::getRid).collect(Collectors.toList());
        List<AdminRole> adminRoles = new ArrayList<>();
        rids.forEach(rid -> {
            AdminRole adminRole = adminRoleRepo.findAdminRoleById(rid);
            adminRoles.add(adminRole);
        });
        return adminRoles;
    }

    public AdminRole updateRoleStatus(AdminRole role) {
        AdminRole roleInDB = adminRoleRepo.findAdminRoleById(role.getId());
        roleInDB.setEnabled(role.isEnabled());
        return adminRoleRepo.save(roleInDB);
    }

    @Transactional
    public void editRole(@RequestBody AdminRole role) {
        role.setEnabled(true);
        adminRoleRepo.save(role);
    }

    public void deleteRole(String rid) {
        adminRoleRepo.deleteById(rid);
        adminRoleMenuService.deleteByRid(rid);
        adminRolePermissionService.deleteByRid(rid);
    }
}
