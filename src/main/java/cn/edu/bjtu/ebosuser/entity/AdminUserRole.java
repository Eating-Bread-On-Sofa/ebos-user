package cn.edu.bjtu.ebosuser.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "adminUserRole")
public class AdminUserRole {
    @Id
    private String id;
    private String uid;
    private String rid;

    public String getId() {
        return id;
    }

    public String getRid() {
        return rid;
    }

    public String getUid() {
        return uid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
