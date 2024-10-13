package com.minidooray.gateway.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minidooray.gateway.domain.Account;
import com.minidooray.gateway.util.ErrorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String host = "http://localhost:8082";

    public List<Project> getProjectList(String accountId, String path) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("accountId", accountId);

        HttpEntity<Object> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    host + path,
                    HttpMethod.GET,
                    request,
                    new ParameterizedTypeReference<Map<String, Object>>() {},
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
