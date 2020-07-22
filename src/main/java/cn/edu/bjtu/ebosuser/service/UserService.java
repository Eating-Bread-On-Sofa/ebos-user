package cn.edu.bjtu.ebosuser.service;

import cn.edu.bjtu.ebosuser.dao.AdminUserRoleRepo;
import cn.edu.bjtu.ebosuser.dao.UserRepo;
import cn.edu.bjtu.ebosuser.dto.UserDTO;
import cn.edu.bjtu.ebosuser.entity.AdminRole;
import cn.edu.bjtu.ebosuser.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    AdminUserRoleRepo adminUserRoleRepo;
    @Autowired
    AdminRoleService adminRoleService;
    @Autowired
    AdminUserRoleService adminUserRoleService;

    public List<UserDTO> list() {
        List<User> users = userRepo.findAll();

        List<UserDTO> userDTOS = users
                .stream().map(user -> (UserDTO) new UserDTO().convertForm(user)).collect(Collectors.toList());

        userDTOS.forEach(u -> {
            List<AdminRole> roles = adminRoleService.listRolesByUser(u.getUsername());
            u.setRoles(roles);
        });
        return userDTOS;
    }

    public boolean isExit(String username) {
        User user = getByUserName(username);
        return null != user;
    }
    public User getByUserId(String id) {
        return userRepo.findUserById(id);
    }

    public User getByUserName(String username) {
        return userRepo.findUserByUsername(username);
    }

    public User get(String username, String password) {
        return userRepo.findByUsernameAndPassword(username, password);
    }

    public int register(User user) {
        String username = user.getUsername();
        String name = user.getName();
        String phone = user.getPhone();
        String email = user.getEmail();
        String password = user.getPassword();
        username = HtmlUtils.htmlEscape(username);
        user.setUsername(username);
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);
        phone = HtmlUtils.htmlEscape(phone);
        user.setPhone(phone);
        email = HtmlUtils.htmlEscape(email);
        user.setEmail(email);
        user.setEnabled(true);

        if (username.equals("") || password.equals("")) {
            return 0;
        }
        boolean exist = isExit(username);
        if (exist) {
            return 2;
        }

        // 默认生成１６位盐
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        // 设置ｈａｓｈ算法迭代次数
        int times = 2;
        // 得到ｈａｓｈ后的密码
        String encodedPassword = new SimpleHash("md5", password, salt, times).toString();
        user.setSalt(salt);
        user.setPassword(encodedPassword);
        userRepo.save(user);
        return 1;
    }

    public void updateUserStatus(User user) {
        User userInDB = userRepo.findUserByUsername(user.getUsername());
        userInDB.setEnabled(user.isEnabled());
        userRepo.save(userInDB);
    }

    public User resetPassword(User user) {
        User userInDB = userRepo.findUserByUsername(user.getUsername());
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        userInDB.setSalt(salt);
        String encodedPassword = new SimpleHash("md5", "123", salt, times).toString();
        userInDB.setPassword(encodedPassword);
        return userRepo.save(userInDB);
    }

    public void editUser(User user) {
        User userInDB = userRepo.findUserByUsername(user.getUsername());
        userInDB.setName(user.getName());
        userInDB.setPhone(user.getPhone());
        userInDB.setEmail(user.getEmail());
        userRepo.save(userInDB);
        adminUserRoleService.saveRoleChanges(userInDB.getId(), user.getRoles());
    }

    public void deleteById(String id) {
        adminUserRoleRepo.deleteAllByUid(id);
        userRepo.deleteById(id);
    }

    public UserDTO getCurrentUserInfo() {
        String username = SecurityUtils.getSubject().getPrincipal().toString();

        User user = getByUserName(username);

        UserDTO userDTO = new UserDTO().convertForm(user);
        List<AdminRole> adminRoles = adminRoleService.listRolesByUser(userDTO.getUsername());
        userDTO.setRoles(adminRoles);

//        // 获得当前用户对应的所有角色的 rid 列表
//        List<String> rids = adminUserRoleService.listAllByUid(user.getId())
//                .stream().map(AdminUserRole::getRid).collect(Collectors.toList());
//        List<AdminRole> roles = new ArrayList<>();
//        rids.forEach(rid -> {
//            AdminRole adminRole = adminRoleService.findByRid(rid);
//            roles.add(adminRole);
//        });
//        user.setRoles(roles);
        return userDTO;
    }

    public int updateUserInfo(User user) {
        if (userRepo.findUserByUsername(user.getUsername()) != null) {
            return 0;
        } else if (user.getUsername() == "") {
            return 2;
        } else {
            User userInDB = userRepo.findUserById(user.getId());
            userInDB.setUsername(user.getUsername());
            userInDB.setName(user.getName());
            userInDB.setPhone(user.getPhone());
            userInDB.setEmail(user.getEmail());

            userRepo.save(userInDB);
            return 1;
        }

    }
}
