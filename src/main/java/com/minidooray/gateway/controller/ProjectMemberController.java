package com.minidooray.gateway.controller;

import com.minidooray.gateway.dto.ProjectMemberRegisterDto;
import com.minidooray.gateway.service.ProjectService;
import com.minidooray.gateway.util.AuthenticationUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectMemberController {
    private final ProjectService projectService;

    @PostMapping("/{projectId}/members")
    public String registerProjectMember(
            @PathVariable("projectId") String projectId,
            @ModelAttribute ProjectMemberRegisterDto projectMemberDto,
            HttpServletRequest request
    ) {
        projectService.registerProjectMember(projectId, projectMemberDto, AuthenticationUtils.getAccountId(), request.getRequestURI());
        return "redirect:/projects";
    }
}
