package com.minidooray.gateway.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minidooray.gateway.domain.Account;
import com.minidooray.gateway.domain.Project;
import com.minidooray.gateway.dto.ProjectMemberRegisterDto;
import com.minidooray.gateway.dto.ProjectRegisterDto;
import com.minidooray.gateway.dto.ProjectUpdateDto;
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
            return objectMapper.convertValue(responseBody.get("data"), new TypeReference<List<Project>>() {});
        } catch (Exception e) {
            ErrorUtils.handleError(e, objectMapper);
        }
        return null;
    }

    public void registerProject(ProjectRegisterDto projectDto, String accountId, String path) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("accountId", accountId);

        HttpEntity<Object> request = new HttpEntity<>(projectDto, headers);

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

    public Project getProject(String accountId, String projectId) {
        String url = host + "/projects/{projectId}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("accountId", accountId);

        HttpEntity<Object> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    new ParameterizedTypeReference<Map<String, Object>>() {},
                    accountId
            );
            Map<String, Object> responseBody = response.getBody();
            return objectMapper.convertValue(responseBody.get("data"), Project.class);
        } catch (Exception e) {
            ErrorUtils.handleError(e, objectMapper);
        }
        return null;
    }

    public void updateProject(ProjectUpdateDto projectDto, String accountId, String path) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("accountId", accountId);

        HttpEntity<Object> request = new HttpEntity<>(projectDto, headers);

        try {
            restTemplate.exchange(
                    host + path,
                    HttpMethod.PUT,
                    request,
                    new ParameterizedTypeReference<Map<String, Object>>() {},
                    accountId
            );
        } catch (Exception e) {
            ErrorUtils.handleError(e, objectMapper);
        }
    }

    public void deleteProject(String accountId, String path) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("accountId", accountId);

        HttpEntity<Object> request = new HttpEntity<>(headers);

        try {
            restTemplate.exchange(
                    host + path,
                    HttpMethod.DELETE,
                    request,
                    new ParameterizedTypeReference<Map<String, Object>>() {},
                    accountId
            );
        } catch (Exception e) {
            ErrorUtils.handleError(e, objectMapper);
        }
    }

    public void registerProjectMember(String projectId, ProjectMemberRegisterDto projectMemberDto, String accountId, String path) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("accountId", accountId);

        HttpEntity<Object> request = new HttpEntity<>(projectMemberDto, headers);

        try {
            restTemplate.exchange(
                    host + path,
                    HttpMethod.POST,
                    request,
                    new ParameterizedTypeReference<Map<String, Object>>() {},
                    projectId
            );
        } catch (Exception e) {
            ErrorUtils.handleError(e, objectMapper);
        }
    }
}
