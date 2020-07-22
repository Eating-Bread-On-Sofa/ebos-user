package cn.edu.bjtu.ebosuser.dao;

import cn.edu.bjtu.ebosuser.entity.AdminRoleMenu;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRoleMenuRepo extends MongoRepository<AdminRoleMenu,String> {

    List<AdminRoleMenu> findAdminRoleMenusByRoleIdNumber(String roleIdNumber);

    void deleteAllByRoleIdNumber(String roleIdNumber);
}
