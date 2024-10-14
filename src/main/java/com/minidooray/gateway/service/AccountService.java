package com.minidooray.gateway.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    public void registerAccount(AccountDto accountDto, String path) {
        AccountDto encodeAccountDto = AccountDto.encodePasswordAccount(accountDto, passwordEncoder);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(encodeAccountDto, headers);

        try {
            Map<String, Object> responseBody = restTemplate.exchange(
                            ACCOUNTS_HOST + path,
                            HttpMethod.POST,
                            request,
                            new ParameterizedTypeReference<Map<String, Object>>() {
                            }
                    )
                    .getBody();
            AccountRegisterResponseDto responseDto = objectMapper.convertValue(responseBody.get("data"),
                                                                               AccountRegisterResponseDto.class);

            request = new HttpEntity<>(headers);
            restTemplate.exchange(
                    TASK_HOST + "/{accountId}",
                    HttpMethod.POST,
                    request,
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    },
                    responseDto.getAccountId()
            );
        } catch (Exception e) {
            ErrorUtils.handleError(e, objectMapper);
        }
    }

    public Account getAccountByUserId(String userId) {
        String url = ACCOUNTS_HOST + "/accounts/{userId}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    },
                    userId
            );
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
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    ACCOUNTS_HOST + path,
                    HttpMethod.GET,
                    request,
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    },
                    accountId
            );

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
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    ACCOUNTS_HOST + path,
                    HttpMethod.PATCH,
                    request,
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    },
                    accountId
            );

            Map<String, Object> responseBody = response.getBody();
            return objectMapper.convertValue(responseBody.get("data"), Account.class);
        } catch (Exception e) {
            ErrorUtils.handleError(e, objectMapper);
        }
        return null;
    }
}
