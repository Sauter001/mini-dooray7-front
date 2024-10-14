package com.minidooray.gateway.client;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "taskClient", url = "${tasks.account-host}")
public interface TaskClient {
    String APPLICATION_JSON_HEADER = "Content-Type: application/json";

    @PostMapping("/{accountId}")
    @Headers(APPLICATION_JSON_HEADER)
    void registerAccountToMember(@PathVariable Long accountId);
}
