package com.refreshing.beer.web.controller;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private String errorCode;

    public NotFoundException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}