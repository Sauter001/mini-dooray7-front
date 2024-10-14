package com.minidooray.gateway.client;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "accountRegisterClient", url = "${accounts.host}")
public interface AccountRegisterClient {
    String APPLICATION_JSON_HEADER = "Content-Type: application/json";

    @PostMapping("/{path}")
    @Headers(APPLICATION_JSON_HEADER)
    Map<String, Object> registerAccount(@PathVariable("path") String path, @RequestBody Object requestBody);
}
