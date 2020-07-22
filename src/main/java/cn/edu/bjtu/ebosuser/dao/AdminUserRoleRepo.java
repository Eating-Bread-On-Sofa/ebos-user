package cn.edu.bjtu.ebosuser.dao;

import cn.edu.bjtu.ebosuser.entity.AdminUserRole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminUserRoleRepo extends MongoRepository<AdminUserRole,String> {
    List<AdminUserRole> findByUid(String uid);
    void deleteAllByUid(String uid);
}
