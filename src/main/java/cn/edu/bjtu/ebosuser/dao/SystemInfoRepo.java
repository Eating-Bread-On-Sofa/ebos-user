package cn.edu.bjtu.ebosuser.dao;

import cn.edu.bjtu.ebosuser.entity.SystemInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemInfoRepo extends MongoRepository<SystemInfo,String> {
    List<SystemInfo> findAll();
    void deleteAll();
}
