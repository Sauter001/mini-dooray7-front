package com.minidooray.gateway.controller;

import com.minidooray.gateway.domain.Project;
import com.minidooray.gateway.dto.ProjectRegisterDto;
import com.minidooray.gateway.dto.ProjectUpdateDto;
import com.minidooray.gateway.service.ProjectService;
import com.minidooray.gateway.util.AuthenticationUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping
    public String getProjectList(Model model, HttpServletRequest request) {
        List<Project> proejctList = projectService.getProjectList(AuthenticationUtils.getAccountId(), request.getRequestURI());
        model.addAttribute("projectList", proejctList);
        return "projectList";
    }

    @PostMapping
    public String registerProject(
            @ModelAttribute ProjectRegisterDto projectDto,
            HttpServletRequest request
    ) {
        projectService.registerProject(projectDto, AuthenticationUtils.getAccountId(), request.getRequestURI());
        return "redirect:/projects";
    }

    @PutMapping("/{projectId}")
    public String updateProject(
            @ModelAttribute ProjectUpdateDto projectDto,
            HttpServletRequest request
    ) {
        projectService.updateProject(projectDto, AuthenticationUtils.getAccountId(), request.getRequestURI());
        return "redirect:/projects";
    }

    @DeleteMapping("/{projectId}")
    public String deleteProject(HttpServletRequest request) {
        projectService.deleteProject(AuthenticationUtils.getAccountId(), request.getRequestURI());
        return "redirect:/projects";
    }
}
