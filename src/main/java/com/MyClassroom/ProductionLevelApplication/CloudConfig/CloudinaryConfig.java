package com.MyClassroom.ProductionLevelApplication.CloudConfig;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", System.getenv("CLOUDINARY_NAME"),
                "api_key", System.getenv("CLOUDINARY_KEY"),
                "api_secret", System.getenv("CLOUDINARY_SECRET")
        ));
    }
}
