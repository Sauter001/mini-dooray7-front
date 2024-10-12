package com.minidooray.gateway.domain;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    private String accountId;
    private String userId;
    private String password;
    private String email;
    private String status;
}
