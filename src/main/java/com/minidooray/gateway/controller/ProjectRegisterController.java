package com.minidooray.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/projects/register")
public class ProjectRegisterController {
    @GetMapping
    public String registerProjectForm() {
        return "registerProject";
    }
}
