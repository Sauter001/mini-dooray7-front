package com.minidooray.gateway.controller;

import com.minidooray.gateway.domain.Account;
import com.minidooray.gateway.dto.AccountStatusDto;
import com.minidooray.gateway.service.AccountService;
import com.minidooray.gateway.util.AuthenticationUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping
    public String getAccount(Model model, HttpServletRequest request) {
        Account account = accountService.getAccountByAccountId(AuthenticationUtils.getAccountId(), request.getRequestURI());
        model.addAttribute("account", account);
        return "mypage";
    }

    @PatchMapping
    public String updateAccountStatus(
            @RequestBody AccountStatusDto statusDto,
            Model model,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Account account = accountService.updateStatus(AuthenticationUtils.getAccountId(), statusDto, request.getRequestURI());
        if (account.getStatus().equals("WITHDRAWN")) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null) {
                new SecurityContextLogoutHandler().logout(request, response, authentication);
            }
        }

        model.addAttribute("account", account);
        return "mypage";
    }
}
