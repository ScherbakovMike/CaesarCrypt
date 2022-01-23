package ru.mikescherbakov.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class CommonsMultipartResolverConfig {

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(SettingsManager.getInt("MAX_UPLOAD_SIZE"));
        return multipartResolver;
    }
}
