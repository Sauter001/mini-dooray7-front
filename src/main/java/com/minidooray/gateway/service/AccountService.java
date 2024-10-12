package com.minidooray.gateway.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minidooray.gateway.domain.Account;
import com.minidooray.gateway.dto.AccountDto;
import com.minidooray.gateway.dto.AccountStatusDto;
import com.minidooray.gateway.dto.ErrorResponse;
import com.minidooray.gateway.exception.AccountException;
import com.minidooray.gateway.util.ErrorUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class AccountService {
    private final RestTemplate restTemplate;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpHeaders headers = new HttpHeaders();

    private static final String host = "http://localhost:8081";

    public AccountService(RestTemplate restTemplate, PasswordEncoder passwordEncoder) {
        this.restTemplate = restTemplate;
        this.passwordEncoder = passwordEncoder;
        this.headers.setContentType(MediaType.APPLICATION_JSON);
    }

    public void registerAccount(AccountDto accountDto, String path) {
        AccountDto encodeAccountDto = AccountDto.encodePasswordAccount(accountDto, passwordEncoder);
        HttpEntity<Object> request = new HttpEntity<>(encodeAccountDto, headers);

        try {
            restTemplate.exchange(
                    host + path,
                    HttpMethod.POST,
                    request,
                    new ParameterizedTypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            ErrorUtils.handleError(e, objectMapper);
        }
    }

    public Account getAccountByUserId(String userId) {
        String url = host + "/accounts/userId/{userId}";
        HttpEntity<Object> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    new ParameterizedTypeReference<Map<String, Object>>() {},
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
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        headers.set("account-id", ((Account) authentication.getPrincipal()).getAccountId());
        HttpEntity<Object> request = new HttpEntity<>(headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                host + path,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<Map<String, Object>>() {},
                accountId
        );

        Map<String, Object> responseBody = response.getBody();
        if (responseBody != null && responseBody.get("code") instanceof Integer) {
            int code = (int) responseBody.get("code");

            if (code == 200) {
                return objectMapper.convertValue(responseBody.get("data"), Account.class);
            }

            if (code == 404) {
                ErrorResponse errorResponse = objectMapper.convertValue(responseBody, ErrorResponse.class);
                throw new AccountException(code, errorResponse);
            }
        }
        return null;
    }

    public Account updateStatus(String accountId, AccountStatusDto statusDto, String path) {
        HttpEntity<Object> request = new HttpEntity<>(statusDto, headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                host + path,
                HttpMethod.PATCH,
                request,
                new ParameterizedTypeReference<Map<String, Object>>() {},
                accountId
        );

        Map<String, Object> responseBody = response.getBody();
        if (responseBody != null && responseBody.get("code") instanceof Integer) {
            int code = (int) responseBody.get("code");

            if (code == 200) {
                return objectMapper.convertValue(responseBody.get("data"), Account.class);
            }

            if (code == 404) {
                ErrorResponse errorResponse = objectMapper.convertValue(responseBody, ErrorResponse.class);
                throw new AccountException(code, errorResponse);
            }
        }
        return null;
    }
}
