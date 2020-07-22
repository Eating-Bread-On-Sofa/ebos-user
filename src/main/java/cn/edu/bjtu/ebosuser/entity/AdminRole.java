package cn.edu.bjtu.ebosuser.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "adminRole")
public class AdminRole {

    @Id
    private String id;
    private String name;
    private String nameZh;

    private boolean enabled;

    @Transient
    private List<AdminPermission> perms;

    @Transient
    private List<AdminMenu> menus;

    @Transient
    private List<String> permIds;

    @Transient
    private List<String> menuIds;


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<AdminPermission> getPerms() {
        return perms;
    }

    public void setPerms(List<AdminPermission> perms) {
        this.perms = perms;
    }

    public List<AdminMenu> getMenus() {
        return menus;
    }

    public void setMenus(List<AdminMenu> menus) {
        this.menus = menus;
    }

    public List<String> getPermIds() {
        return permIds;
    }

    public void setPermIds(List<String> permIds) {
        this.permIds = permIds;
    }

    public List<String> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<String> menuIds) {
        this.menuIds = menuIds;
    }
}
