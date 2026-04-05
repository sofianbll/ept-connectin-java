package com.connect_in.backend.shared.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Expose /storage (avatars, post images) via HTTP.
        registry.addResourceHandler("/storage/**")
                .addResourceLocations("file:storage/");

        // Handle double /storage/ prefix (PostItem.jsx bug workaround)
        registry.addResourceHandler("/storage/storage/**")
                .addResourceLocations("file:storage/");
    }
}
