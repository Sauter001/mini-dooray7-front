package com.minidooray.gateway.dto;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class AccountDto {
    private String userId;
    private String password;
    private String email;

    public static AccountDto encodePasswordAccount(AccountDto accountDto, PasswordEncoder passwordEncoder) {
        accountDto.setPassword(passwordEncoder.encode(accountDto.password));
        return accountDto;
    }
}
