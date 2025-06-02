package com.example.myapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    String uploadPath = System.getProperty("user.home") + "/myapp/uploads/";

    registry.addResourceHandler("/uploads/**").addResourceLocations("file:" + uploadPath);
  }
}
