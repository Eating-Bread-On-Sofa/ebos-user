package cn.edu.bjtu.ebosuser.service;


import cn.edu.bjtu.ebosuser.dao.AdminMenuRepo;
import cn.edu.bjtu.ebosuser.entity.AdminMenu;
import cn.edu.bjtu.ebosuser.entity.AdminRoleMenu;
import cn.edu.bjtu.ebosuser.entity.AdminUserRole;
import cn.edu.bjtu.ebosuser.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminMenuService {

    @Autowired
    AdminMenuRepo adminMenuRepo;
    @Autowired
    UserService userService;
    @Autowired
    AdminUserRoleService adminUserRoleService;
    @Autowired
    AdminRoleMenuService adminRoleMenuService;

    public AdminMenu getMenuById(String mid) {
        return adminMenuRepo.findAdminMenuById(mid);
    }

    public List<AdminMenu> getAllByParentId(String parentId) {
        return adminMenuRepo.findByParentId(parentId);
    }

    public List<AdminMenu> getAllMenus() {
        return adminMenuRepo.findAll();
    }

    public Map<String, List<AdminMenu>> listMenuTree() {
        List<AdminMenu> Menus = adminMenuRepo.findAll();
        List<AdminMenu> commonMenus = new ArrayList<>();
        List<AdminMenu> adminMenus = new ArrayList<>();
        List<AdminMenu> otherMenus = new ArrayList<>();
        Menus.forEach(menu -> {
            if (menu.getCommonOrAdmin().equals("admin")) {
                adminMenus.add(menu);
            } else if (menu.getCommonOrAdmin().equals("common")) {
                commonMenus.add(menu);
            } else {
                otherMenus.add(menu);
            }
        });
        Map<String, List<AdminMenu>> map = new LinkedHashMap<>();
        map.put("common", handleMenus(commonMenus));
        map.put("admin", handleMenus(adminMenus));

        return map;
    }

    public Map<String, List<AdminMenu>> getMenusByCurrentUser() {
        // 从数据库中获取当前用户
        String username = SecurityUtils.getSubject().getPrincipal().toString();

        User user = userService.getByUserName(username);

        // 获得当前用户对应的所有角色的 rid 列表
        List<String> rids = adminUserRoleService.listAllByUid(user.getId())
                .stream().map(AdminUserRole::getRid).collect(Collectors.toList());


        // 从角色列表中获得菜单项
        List<String> menuIds = adminRoleMenuService.findAllByRid(rids)
                .stream().map(AdminRoleMenu::getMenuIdNumber).collect(Collectors.toList());
        Set<String> setMenuIds = new LinkedHashSet<>();
        menuIds.forEach(mid -> {
            setMenuIds.add(mid);
        });
//        List<AdminMenu> menus = adminMenuRepo.findById(menuIds).stream().distinct().collect(Collectors.toList());
        List<AdminMenu> menus = new ArrayList<>();
        setMenuIds.forEach(mid -> {
            AdminMenu adminMenu = adminMenuRepo.findAdminMenuById(mid);
            menus.add(adminMenu);

        });
        List<AdminMenu> commonMenus = new ArrayList<>();
        List<AdminMenu> adminMenus = new ArrayList<>();
        List<AdminMenu> otherMenus = new ArrayList<>();
        menus.forEach(menu -> {
            if (menu.getCommonOrAdmin().equals("admin")) {
                adminMenus.add(menu);
            } else if (menu.getCommonOrAdmin().equals("common")) {
                commonMenus.add(menu);
            } else {
                otherMenus.add(menu);
            }
        });
        Map<String, List<AdminMenu>> map = new LinkedHashMap<>();
        map.put("common", handleMenus(commonMenus));
        map.put("admin", handleMenus(adminMenus));


        // 处理菜单项的结构
        return map;
    }

    public List<AdminMenu> getMenusByRoleId(String rid) {
        List<String> menuIds = adminRoleMenuService.findAllByRid(rid)
                .stream().map(AdminRoleMenu::getMenuIdNumber).collect(Collectors.toList());
        List<AdminMenu> menus = new ArrayList<>();
        menuIds.forEach(mid -> {
            AdminMenu adminMenu = adminMenuRepo.findAdminMenuById(mid);
            menus.add(adminMenu);
        });
//        handleMenus(menus);
        return handleMenus(menus);
    }

    public List<AdminMenu> handleMenus(List<AdminMenu> menus) {
//        menus.forEach(m -> {
//            List<AdminMenu> children = getAllByParentId(m.getId());
//            m.setChildren(children);
//        });
        menus.removeIf(m -> m.getParentId().length() == 0);
        List<AdminMenu> newMenu = new ArrayList<>();
        Map<String, List<AdminMenu>> map = new HashMap<>();
        menus.forEach(m -> {
            String parentId = m.getParentId();
            if (map.containsKey(parentId)) {
                map.get(parentId).add(m);
            } else {
                List<AdminMenu> children = new ArrayList<>();
                children.add(m);
                map.put(parentId, children);
            }
        });
        map.forEach((key, value) -> {
            AdminMenu adminMenu = adminMenuRepo.findAdminMenuById(key);
            adminMenu.setChildren(value);
            newMenu.add(adminMenu);
        });
        Collections.sort(newMenu, new MyComparator());
        return newMenu;
    }

    class MyComparator implements Comparator<AdminMenu> {
        public int compare(AdminMenu adminMenu1, AdminMenu adminMenu2) {
            int result = Integer.valueOf(adminMenu1.getIndex()) - Integer.valueOf(adminMenu2.getIndex());
            return result;
        }
    }

    public void addMenu(AdminMenu adminMenu) {
        String path = adminMenu.getPath();
        String name = adminMenu.getName();
        String nameZh = adminMenu.getNameZh();
        String iconCls = adminMenu.getIconCls();
        String component = adminMenu.getComponent();
        String parentId = adminMenu.getParentId();
        String commonOrAdmin = adminMenu.getCommonOrAdmin();

        path = HtmlUtils.htmlEscape(path);
        name = HtmlUtils.htmlEscape(name);
        nameZh = HtmlUtils.htmlEscape(nameZh);
        iconCls = HtmlUtils.htmlEscape(iconCls);
        component = HtmlUtils.htmlEscape(component);
        parentId = HtmlUtils.htmlEscape(parentId);
        commonOrAdmin = HtmlUtils.htmlEscape(commonOrAdmin);

        adminMenu.setPath(path);
        adminMenu.setName(name);
        adminMenu.setNameZh(nameZh);
        adminMenu.setIconCls(iconCls);
        adminMenu.setComponent(component);
        adminMenu.setParentId(parentId);
        adminMenu.setCommonOrAdmin(commonOrAdmin);

        adminMenuRepo.save(adminMenu);
    }
}
