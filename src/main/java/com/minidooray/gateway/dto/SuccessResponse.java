package com.minidooray.gateway.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuccessResponse {
    private int code;
    private Object data;
}
