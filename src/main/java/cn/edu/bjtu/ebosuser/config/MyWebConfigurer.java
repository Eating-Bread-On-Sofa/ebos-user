package cn.edu.bjtu.ebosuser.config;

import cn.edu.bjtu.ebosuser.service.SystemInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.*;

import java.io.File;

@SpringBootConfiguration
public class MyWebConfigurer implements WebMvcConfigurer {
    @Autowired
    SystemInfoService systemInfoService;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
      // 所有请求都允许跨域，使用这种配置方法就不能在 interceptor 中再配置 header 了
      registry.addMapping("/**")
              .allowCredentials(true)
              .allowedOrigins("*")
              .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
              .allowedHeaders("*")
              .maxAge(36000);
  }

    private String getLogoImgFolder() {
        return systemInfoService.getThisJarPath() + File.separator + "logoImg";
    }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
      String path = getLogoImgFolder();
      registry.addResourceHandler("/api/file/**").addResourceLocations("file:" + path + "/");
  }

}
