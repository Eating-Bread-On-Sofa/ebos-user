package cn.edu.bjtu.ebosuser.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "adminPermission")
public class AdminPermission {
    @Id
    private String id;
    private String name;
    private String desc_;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc_() {
        return desc_;
    }

    public void setDesc_(String desc_) {
        this.desc_ = desc_;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
