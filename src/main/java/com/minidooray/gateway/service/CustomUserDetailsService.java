package com.minidooray.gateway.service;

import com.minidooray.gateway.domain.Account;
import com.minidooray.gateway.domain.CustomUserDetails;
import com.minidooray.gateway.exception.AccountException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = null;
        try {
            account = accountService.getAccountByUserId(username);
        } catch (AccountException e) {
            throw new UsernameNotFoundException(e.getErrorMessage().getMessage());
        }
        return new CustomUserDetails(account);
    }
}
