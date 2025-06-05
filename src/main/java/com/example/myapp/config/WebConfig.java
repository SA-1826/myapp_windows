package com.example.myapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.lang.NonNull;
import org.springframework.http.CacheControl;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
    String uploadPath = System.getProperty("user.home") + "/myapp/uploads/";

    registry.addResourceHandler("/uploads/**").addResourceLocations("file:" + uploadPath).setCacheControl(CacheControl.noStore());
  }
}
