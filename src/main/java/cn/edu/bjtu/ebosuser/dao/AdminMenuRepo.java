package cn.edu.bjtu.ebosuser.dao;

import cn.edu.bjtu.ebosuser.entity.AdminMenu;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminMenuRepo extends MongoRepository<AdminMenu,String> {
    AdminMenu findAdminMenuById(String id);
    List<AdminMenu> findByParentId(String parentId);
}
