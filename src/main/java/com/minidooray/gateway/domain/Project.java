package com.minidooray.gateway.domain;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class Project implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long projectId;
    private Long accountId;
    private String projectName;
    private String projectStatus;
}
