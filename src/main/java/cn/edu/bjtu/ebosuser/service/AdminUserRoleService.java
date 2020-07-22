package cn.edu.bjtu.ebosuser.service;

import cn.edu.bjtu.ebosuser.entity.AdminRole;
import cn.edu.bjtu.ebosuser.entity.AdminUserRole;
import cn.edu.bjtu.ebosuser.dao.AdminUserRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminUserRoleService {
    @Autowired
    AdminUserRoleRepo adminUserRoleRepo;

    public List<AdminUserRole> listAllByUid(String uid) {
        return adminUserRoleRepo.findByUid(uid);
    }

    @Transactional
    public void saveRoleChanges(String uid, List<AdminRole> roles) {
        adminUserRoleRepo.deleteAllByUid(uid);
        List<AdminUserRole> urs = new ArrayList<>();
        roles.forEach(r -> {
            AdminUserRole ur = new AdminUserRole();
            ur.setUid(uid);
            ur.setRid(r.getId());
            urs.add(ur);
        });
        adminUserRoleRepo.saveAll(urs);
    }
}
