package cn.edu.bjtu.ebosuser.dao;

import cn.edu.bjtu.ebosuser.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepo extends MongoRepository<User,String> {

    User findUserByUsername(String username);

    User findUserById(String id);

    User findByUsernameAndPassword(String username,String password);


}
