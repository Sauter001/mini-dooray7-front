package com.minidooray.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {
    @Bean
    public feign.Client feignClient() {
        return new feign.okhttp.OkHttpClient();
    }
}
