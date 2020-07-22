package cn.edu.bjtu.ebosuser.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "adminRoleMenu")
public class AdminRoleMenu {

    @Id
    private String id;
    private String roleIdNumber;
    private String menuIdNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleIdNumber() {
        return roleIdNumber;
    }

    public void setRoleIdNumber(String roleIdNumber) {
        this.roleIdNumber = roleIdNumber;
    }

    public String getMenuIdNumber() {
        return menuIdNumber;
    }

    public void setMenuIdNumber(String menuIdNumber) {
        this.menuIdNumber = menuIdNumber;
    }
}
