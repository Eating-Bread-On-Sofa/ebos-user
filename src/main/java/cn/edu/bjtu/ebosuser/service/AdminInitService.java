package cn.edu.bjtu.ebosuser.service;

import cn.edu.bjtu.ebosuser.dao.*;
import cn.edu.bjtu.ebosuser.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
public class AdminInitService {
    @Autowired
    AdminRoleRepo adminRoleRepo;
    @Autowired
    AdminRoleMenuRepo adminRoleMenuRepo;
    @Autowired
    AdminMenuRepo adminMenuRepo;
    @Autowired
    AdminRolePermissionRepo adminRolePermissionRepo;
    @Autowired
    AdminPermissionRepo adminPermissionRepo;
    @Autowired
    AdminUserRoleRepo adminUserRoleRepo;

    public boolean roleIsExist(String name) {
        AdminRole adminRole = getByRoleName(name);
//        AdminRole adminRole1 = adminRoleRepo.findAllByNameZh(roleNameZh);
        return null != adminRole;
    }

    public AdminRole getByRoleName(String name) {
        AdminRole adminRole = adminRoleRepo.findAdminRoleByNameZh(name);
        return adminRole;
    }

    public int addRole(AdminRole adminRole) {
        String name = adminRole.getName();
        String nameZh = adminRole.getNameZh();
        name = HtmlUtils.htmlEscape(name);
        nameZh = HtmlUtils.htmlEscape(nameZh);
        adminRole.setName(name);
        adminRole.setNameZh(nameZh);
        adminRole.setEnabled(true);
        if (name.equals("") || nameZh.equals("")) {
            return 0;
        }
//        boolean exist = roleIsExist(name);
//        System.out.println("3" + exist);
//        if (exist) {
//            return 3;
//        }

        adminRoleRepo.save(adminRole);
        return 1;
    }

    public int addMenu(AdminMenu adminMenu) {
        String path = adminMenu.getPath();
        String name = adminMenu.getName();
        String nameZh = adminMenu.getNameZh();
        String iconCls = adminMenu.getIconCls();
        String component = adminMenu.getComponent();
        String parentId = adminMenu.getParentId();

        path = HtmlUtils.htmlEscape(path);
        name = HtmlUtils.htmlEscape(name);
        nameZh = HtmlUtils.htmlEscape(nameZh);
        iconCls = HtmlUtils.htmlEscape(iconCls);
        component = HtmlUtils.htmlEscape(component);
        parentId = HtmlUtils.htmlEscape(parentId);

        adminMenu.setPath(path);
        adminMenu.setName(name);
        adminMenu.setNameZh(nameZh);
        adminMenu.setIconCls(iconCls);
        adminMenu.setComponent(component);
        adminMenu.setParentId(parentId);

        adminMenuRepo.save(adminMenu);
        return 1;
    }

    public int addRoleMenu(AdminRoleMenu adminRoleMenu) {
        String rid = adminRoleMenu.getRoleIdNumber();
        String mid = adminRoleMenu.getMenuIdNumber();

        rid = HtmlUtils.htmlEscape(rid);
        mid = HtmlUtils.htmlEscape(mid);

        adminRoleMenu.setRoleIdNumber(rid);
        adminRoleMenu.setMenuIdNumber(mid);

        adminRoleMenuRepo.save(adminRoleMenu);
        return 1;
    }

    public int addUserRole(AdminUserRole adminUserRole) {
        String uid = adminUserRole.getUid();
        String rid = adminUserRole.getRid();

        rid = HtmlUtils.htmlEscape(rid);
        uid = HtmlUtils.htmlEscape(uid);

        adminUserRole.setRid(rid);
        adminUserRole.setUid(uid);

        adminUserRoleRepo.save(adminUserRole);
        return 1;
    }

    public int addRolePerms(AdminRolePermission adminRolePermission) {
        String rid = adminRolePermission.getRid();
        String pid = adminRolePermission.getPid();

        rid = HtmlUtils.htmlEscape(rid);
        pid = HtmlUtils.htmlEscape(pid);

        adminRolePermission.setRid(rid);
        adminRolePermission.setPid(pid);

        adminRolePermissionRepo.save(adminRolePermission);
        return 1;
    }

    public int addPermission(AdminPermission adminPermission) {
        String name = adminPermission.getName();
        String desc_ = adminPermission.getDesc_();
        String url = adminPermission.getUrl();

        name = HtmlUtils.htmlEscape(name);
        desc_ = HtmlUtils.htmlEscape(desc_);
        url = HtmlUtils.htmlEscape(url);

        adminPermission.setName(name);
        adminPermission.setDesc_(desc_);
        adminPermission.setUrl(url);

        adminPermissionRepo.save(adminPermission);
        return 1;
    }
}
