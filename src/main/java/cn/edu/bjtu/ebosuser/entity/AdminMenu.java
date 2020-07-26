package cn.edu.bjtu.ebosuser.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "adminMenu")
public class AdminMenu {

    @Id
    private String id;
    private String path;
    private String name;
    private String nameZh;
    private String iconCls;
    private String component;
    private String parentId;
    private String commonOrAdmin;
    private String index;

    @Transient
    private List<AdminMenu> children;

    public String getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public String getNameZh() {
        return nameZh;
    }

    public String getIconCls() {
        return iconCls;
    }

    public String getComponent() {
        return component;
    }

    public String getParentId() {
        return parentId;
    }

    public List<AdminMenu> getChildren() {
        return children;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setChildren(List<AdminMenu> children) {
        this.children = children;
    }

    public String getCommonOrAdmin() {
        return commonOrAdmin;
    }

    public void setCommonOrAdmin(String commonOrAdmin) {
        this.commonOrAdmin = commonOrAdmin;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
