package cn.edu.bjtu.ebosuser.dao;

import cn.edu.bjtu.ebosuser.entity.AdminRolePermission;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AdminRolePermissionRepo extends MongoRepository<AdminRolePermission, String> {

    List<AdminRolePermission> findAdminRolePermissionsByRid(String rid);

    void deleteAllByRid(String rid);
}
