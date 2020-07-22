package cn.edu.bjtu.ebosuser.dto;

import cn.edu.bjtu.ebosuser.dto.base.OutputConverter;
import cn.edu.bjtu.ebosuser.entity.AdminRole;
import cn.edu.bjtu.ebosuser.entity.User;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class UserDTO implements OutputConverter<UserDTO, User> {

    private String id;
    private String username;
    private String name;
    private String phone;
    private String email;
    private boolean enabled;
    private List<AdminRole> roles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<AdminRole> getRoles() {
        return roles;
    }

    public void setRoles(List<AdminRole> roles) {
        this.roles = roles;
    }
}
