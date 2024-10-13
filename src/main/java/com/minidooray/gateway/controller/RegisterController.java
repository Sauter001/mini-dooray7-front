package com.minidooray.gateway.controller;

import com.minidooray.gateway.dto.AccountDto;
import com.minidooray.gateway.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {
    private final AccountService accountService;

    @GetMapping
    public String registerForm() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/projects";
        }
        return "register";
    }

    @PostMapping
    public String register(
            @ModelAttribute AccountDto accountDto,
            HttpServletRequest request
    ) {
        accountService.registerAccount(accountDto, request.getRequestURI());
        return "redirect:/login";
    }
}
