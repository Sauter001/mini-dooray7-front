package com.minidooray.gateway.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minidooray.gateway.client.AccountClient;
import com.minidooray.gateway.client.AccountRegisterClient;
import com.minidooray.gateway.client.TaskClient;
import com.minidooray.gateway.domain.Account;
import com.minidooray.gateway.dto.AccountDto;
import com.minidooray.gateway.dto.AccountRegisterResponseDto;
import com.minidooray.gateway.dto.AccountStatusDto;
import com.minidooray.gateway.util.ErrorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AccountService {
    private static final String ACCOUNTS_HOST = "http://localhost:8083";
    private static final String TASK_HOST = "http://localhost:8082/members";
    private final RestTemplate restTemplate;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    // feign clients
    private final AccountRegisterClient accountRegisterClient;
    private final AccountClient accountClient;
    private final TaskClient taskClient;

    public void registerAccount(AccountDto accountDto, String path) {
        AccountDto encodeAccountDto = AccountDto.encodePasswordAccount(accountDto, passwordEncoder);

        try {
            path = slicePath(path);
            Map<String, Object> responseBody = accountRegisterClient.registerAccount(path, encodeAccountDto);
            AccountRegisterResponseDto responseDto = objectMapper.convertValue(
                    responseBody.get("data"),
                    AccountRegisterResponseDto.class
            );

            taskClient.registerAccountToMember(responseDto.getAccountId());
        } catch (Exception e) {
            ErrorUtils.handleError(e, objectMapper);
        }
    }

    public Account getAccountByUserId(String userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map<String, Object>> response = accountClient.getAccountByUserId(userId);
            Map<String, Object> responseBody = response.getBody();
            return objectMapper.convertValue(responseBody.get("data"), Account.class);
        } catch (Exception e) {
            ErrorUtils.handleError(e, objectMapper);
        }

        return null;
    }

    public Account getAccountByAccountId(String accountId, String path) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("accountId", accountId);

        HttpEntity<Object> request = new HttpEntity<>(headers);

        try {
            path = slicePath(path);
            ResponseEntity<Map<String, Object>> response = accountClient.getAccountByAccountId(accountId);

            Map<String, Object> responseBody = response.getBody();
            return objectMapper.convertValue(responseBody.get("data"), Account.class);

        } catch (Exception e) {
            ErrorUtils.handleError(e, objectMapper);
        }
        return null;
    }

    public Account updateStatus(String accountId, AccountStatusDto statusDto, String path) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("accountId", accountId);

        HttpEntity<Object> request = new HttpEntity<>(statusDto, headers);

        try {
            ResponseEntity<Map<String, Object>> response = accountClient.updateStatus(accountId, statusDto);

            Map<String, Object> responseBody = response.getBody();
            return objectMapper.convertValue(responseBody.get("data"), Account.class);
        } catch (Exception e) {
            ErrorUtils.handleError(e, objectMapper);
        }
        return null;
    }

    private String slicePath(String path) {
        return path.startsWith("/") ? path.substring(1) : path;
    }
}
