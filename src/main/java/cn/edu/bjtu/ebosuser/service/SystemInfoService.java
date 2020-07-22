package cn.edu.bjtu.ebosuser.service;

import cn.edu.bjtu.ebosuser.dao.SystemInfoRepo;
import cn.edu.bjtu.ebosuser.entity.SystemInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class SystemInfoService {
    @Autowired
    SystemInfoRepo systemInfoRepo;

    public List<SystemInfo> getAllInfo() {
        return systemInfoRepo.findAll();
    }
    public void addInfo(SystemInfo systemInfo) {
        String name = systemInfo.getName();
        String logoUrl = systemInfo.getLogoUrl();
        String nameAdmin = systemInfo.getNameAdmin();

        name = HtmlUtils.htmlEscape(name);
        logoUrl = HtmlUtils.htmlEscape(logoUrl);
        nameAdmin = HtmlUtils.htmlEscape(nameAdmin);

        systemInfo.setName(name);
        systemInfo.setLogoUrl(logoUrl);
        systemInfo.setNameAdmin(nameAdmin);


        systemInfoRepo.deleteAll();
        systemInfoRepo.save(systemInfo);
    }
}
