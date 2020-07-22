package cn.edu.bjtu.ebosuser.service;

import cn.edu.bjtu.ebosuser.dao.AdminPermissionRepo;
import cn.edu.bjtu.ebosuser.dao.AdminRolePermissionRepo;
import cn.edu.bjtu.ebosuser.entity.AdminPermission;
import cn.edu.bjtu.ebosuser.entity.AdminRole;
import cn.edu.bjtu.ebosuser.entity.AdminRolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Permission;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminPermissionService {
    @Autowired
    AdminPermissionRepo adminPermissionRepo;
    @Autowired
    AdminUserRoleService adminUserRoleService;
    @Autowired
    AdminRoleService adminRoleService;
    @Autowired
    AdminRolePermissionService adminRolePermissionService;
    @Autowired
    AdminRolePermissionRepo adminRolePermissionRepo;
    @Autowired
    UserService userService;

    public List<AdminPermission> list() {
        return adminPermissionRepo.findAll();
    }

    public boolean needFilter(String requestAPI){
        List<AdminPermission> ps = adminPermissionRepo.findAll();
        for (AdminPermission p : ps) {
            if (requestAPI.startsWith(p.getUrl())) {
                return true;
            }
        }
        return false;
    }

    public List<AdminPermission> listPermsByRoleId(String rid) {
        List<String> pids = adminRolePermissionService.findAllByRid(rid)
                .stream().map(AdminRolePermission::getPid).collect(Collectors.toList());
        List<AdminPermission> adminPermissions = new ArrayList<>();
        pids.forEach(pid -> {
            AdminPermission adminPermission = adminPermissionRepo.findAdminPermissionById(pid);
            adminPermissions.add(adminPermission);
        });
        return adminPermissions;
    }

    public Set<String> listPermissionURLsByUser(String username) {
        List<String> rids = adminRoleService.listRolesByUser(username)
                .stream().map(AdminRole::getId).collect(Collectors.toList());
        List<String> pids = new ArrayList<>();
        rids.forEach(rid -> {
            List<String> pids1 = adminRolePermissionRepo.findAdminRolePermissionsByRid(rid)
                    .stream().map(AdminRolePermission::getPid).collect(Collectors.toList());
            pids.addAll(pids1);
        });
        List<AdminPermission> perms = new ArrayList<>();
        pids.forEach(pid -> {
            AdminPermission perm = adminPermissionRepo.findAdminPermissionById(pid);
            perms.add(perm);
        });
        Set<String> URLs = perms.stream().map(AdminPermission::getUrl).collect(Collectors.toSet());
        return URLs;

    }


}
