package com.minidooray.gateway.controller;

import com.minidooray.gateway.service.ProjectService;
import com.minidooray.gateway.util.AuthenticationUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping
    public String getProjectList(Model model, HttpServletRequest request) {
        List<Project> proejctList = projectService.getProjectList(AuthenticationUtils.getAccountId(), request.getRequestURI());
    }
}
