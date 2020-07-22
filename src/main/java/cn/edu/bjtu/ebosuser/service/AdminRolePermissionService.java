package cn.edu.bjtu.ebosuser.service;

import cn.edu.bjtu.ebosuser.dao.AdminRolePermissionRepo;
import cn.edu.bjtu.ebosuser.entity.AdminPermission;
import cn.edu.bjtu.ebosuser.entity.AdminRolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminRolePermissionService {
    @Autowired
    AdminRolePermissionRepo adminRolePermissionRepo;

    List<AdminRolePermission> findAllByRid(String id) {
        return adminRolePermissionRepo.findAdminRolePermissionsByRid(id);
    }

    public void deleteByRid(String rid) {
        adminRolePermissionRepo.deleteAllByRid(rid);
    }

    @Transactional
    public void savePermChanges(String rid, List<String> permIds) {
        adminRolePermissionRepo.deleteAllByRid(rid);
        List<AdminRolePermission> rps = new ArrayList<>();
        permIds.forEach(pid -> {
            AdminRolePermission rp = new AdminRolePermission();
            rp.setRid(rid);
            rp.setPid(pid);
            rps.add(rp);
        });
        adminRolePermissionRepo.saveAll(rps);
    }
}
