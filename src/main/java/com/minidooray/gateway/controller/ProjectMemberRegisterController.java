package com.minidooray.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/projects")
public class ProjectMemberRegisterController {
    @GetMapping("/{projectId}/members")
    public String registerProjectMemberForm(@PathVariable("projectId") String projectId, Model model) {
        model.addAttribute("projectId", projectId);
        return "registerMember";
    }
}
