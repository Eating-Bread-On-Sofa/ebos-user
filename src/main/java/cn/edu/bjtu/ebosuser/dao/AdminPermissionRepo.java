package cn.edu.bjtu.ebosuser.dao;

import cn.edu.bjtu.ebosuser.entity.AdminPermission;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface AdminPermissionRepo extends MongoRepository<AdminPermission, String> {
    AdminPermission findAdminPermissionById(String id);
}
