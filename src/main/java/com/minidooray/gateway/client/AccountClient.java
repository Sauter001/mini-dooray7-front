package com.minidooray.gateway.client;

import com.minidooray.gateway.dto.AccountStatusDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "accountClient", url = "${accounts.host}/accounts")
public interface AccountClient {
    String APPLICATION_JSON_HEADER = "Content-Type: application/json";
    String ACCOUNT_ID_KEY = "accountId";

    @GetMapping("/{path}")
    @Headers(APPLICATION_JSON_HEADER)
    ResponseEntity<Map<String, Object>> getAccountByUserId(@PathVariable("path") String path);

    @GetMapping
    @Headers(APPLICATION_JSON_HEADER)
    ResponseEntity<Map<String, Object>> getAccountByAccountId(@RequestHeader(ACCOUNT_ID_KEY) String accountId);

    @PatchMapping
    @Headers(APPLICATION_JSON_HEADER)
    ResponseEntity<Map<String, Object>> updateStatus(@RequestHeader(ACCOUNT_ID_KEY) String accountId,
                                                     @RequestBody AccountStatusDto statusDto);
}
