package com.test.demo.config;




import com.test.demo.common.Constance;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 主要是静态文件的路径映射配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // tomcat映射
        registry.addResourceHandler(Constance.UPLOAD_PATH+"/**")
                .addResourceLocations(Constance.UPLOAD_LOCATION_MAPPING_PATH);
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:E:/myFile/");
    }
}
