package cn.edu.bjtu.ebosuser.dao;

import cn.edu.bjtu.ebosuser.entity.AdminRole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRoleRepo extends MongoRepository<AdminRole,String> {

    AdminRole findAdminRoleById(String id);
    AdminRole findAdminRoleByName(String name);
    AdminRole findAdminRoleByNameZh(String nameZh);
}
