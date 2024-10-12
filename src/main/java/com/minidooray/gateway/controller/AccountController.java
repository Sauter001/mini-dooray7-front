package com.minidooray.gateway.controller;

import com.minidooray.gateway.domain.Account;
import com.minidooray.gateway.dto.AccountDto;
import com.minidooray.gateway.dto.AccountStatusDto;
import com.minidooray.gateway.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public String register(
            @ModelAttribute AccountDto accountDto,
            HttpServletRequest request
    ) {
        accountService.registerAccount(accountDto, request.getRequestURI());
        return "redirect:/login";
    }

    @GetMapping("/{accountId}")
    public String getAccount(
            @PathVariable("accountId") String accountId,
            Model model,
            HttpServletRequest request
    ) {
        Account account = accountService.getAccountByAccountId(accountId, request.getRequestURI());
        model.addAttribute("account", account);
        return "account 조회 페이지 반환하기";
    }

    @PatchMapping("/{accountId}")
    public String updateAccountStatus(
            @PathVariable("accountId") String accountId,
            @RequestBody AccountStatusDto statusDto,
            Model model,
            HttpServletRequest request
    ) {
        Account account = accountService.updateStatus(accountId, statusDto, request.getRequestURI());
        model.addAttribute("account", account);
        return "account 조회 페이지 반환하기";
    }
}
