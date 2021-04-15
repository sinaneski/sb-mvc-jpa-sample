package com.swarts.sb.account.dto;

import lombok.Getter;

@Getter
public class ErrorDto {

    private long status;
    private String message;
    private String description;

    public ErrorDto(long status, String message, String description) {
        this.status = status;
        this.message = message;
        this.description = description;
    }
}
