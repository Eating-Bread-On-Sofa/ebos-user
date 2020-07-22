package cn.edu.bjtu.ebosuser.Controller;

import cn.edu.bjtu.ebosuser.entity.SystemInfo;
import cn.edu.bjtu.ebosuser.result.Result;
import cn.edu.bjtu.ebosuser.result.ResultFactory;
import cn.edu.bjtu.ebosuser.service.SystemInfoService;
import cn.edu.bjtu.ebosuser.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RequestMapping("/api")
@RestController
public class SystemInfoController {
    @Autowired
    SystemInfoService systemInfoService;

    @GetMapping("/system/info")
    public Result getSystemInfo() {
        return ResultFactory.buildSuccessResult(systemInfoService.getAllInfo());
    }

    @PostMapping("/system/logo")
    public String logoUpload(MultipartFile file) {
        String folder = "/home/qds/edgexImg/";
        File imageFolder = new File(folder);
        if (!imageFolder.exists())
            imageFolder.mkdirs();
        File f = new File(imageFolder, StringUtils.getRandomString(6) + file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4));

        try {
            file.transferTo(f);
            String imgUrl = "http://localhost:8093/api/file/" + f.getName();
            return imgUrl;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }

    @PostMapping("/system/info")
    public Result addSystemInfo(@RequestBody SystemInfo systemInfo) {
        systemInfoService.addInfo(systemInfo);
        return ResultFactory.buildSuccessResult("成功更新系统信息");
    }
}
