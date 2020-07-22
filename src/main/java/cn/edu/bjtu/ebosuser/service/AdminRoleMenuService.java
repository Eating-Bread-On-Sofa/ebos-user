package cn.edu.bjtu.ebosuser.service;

import cn.edu.bjtu.ebosuser.dao.AdminRoleMenuRepo;
import cn.edu.bjtu.ebosuser.entity.AdminMenu;
import cn.edu.bjtu.ebosuser.entity.AdminRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AdminRoleMenuService {
    @Autowired
    AdminRoleMenuRepo adminRoleMenuRepo;
    @Autowired
    AdminMenuService adminMenuService;

    public List<AdminRoleMenu> findAllByRid(String rid) {
        return adminRoleMenuRepo.findAdminRoleMenusByRoleIdNumber(rid);
    }

    public List<AdminRoleMenu> findAllByRid(List<String> rids) {
        List<AdminRoleMenu> adminRoleMenus = new ArrayList<>();
        rids.forEach(rid -> {
            List<AdminRoleMenu> adminRoleMenus1 = adminRoleMenuRepo.findAdminRoleMenusByRoleIdNumber(rid);
            adminRoleMenus.addAll(adminRoleMenus1);
        });
        return adminRoleMenus;
    }

    public void save(AdminRoleMenu rm) {
        adminRoleMenuRepo.save(rm);
    }

    public void deleteByRid(String rid) {
        adminRoleMenuRepo.deleteAllByRoleIdNumber(rid);
    }

    @Transactional
    public void updateRoleMenu(String rid, List<String> menuIds){
        adminRoleMenuRepo.deleteAllByRoleIdNumber(rid);
        List<AdminRoleMenu> rms = new ArrayList<>();
        for (String mid : menuIds) {
            AdminRoleMenu rm = new AdminRoleMenu();
            rm.setMenuIdNumber(mid);
            rm.setRoleIdNumber(rid);
            rms.add(rm);
        }
        adminRoleMenuRepo.saveAll(rms);

    }
}
