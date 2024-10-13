package com.minidooray.gateway.controller;

import com.minidooray.gateway.domain.Project;
import com.minidooray.gateway.service.ProjectService;
import com.minidooray.gateway.util.AuthenticationUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/projects/update")
@RequiredArgsConstructor
public class ProjectUpdateController {
    private final ProjectService projectService;

    @GetMapping("/{projectId}")
    public String updateProjectForm(
            @PathVariable("projectId") String projectId,
            Model model
    ) {
        Project project = projectService.getProject(AuthenticationUtils.getAccountId(), projectId);
        model.addAttribute("project", project);
        return "updateProject";
    }
}
