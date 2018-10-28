package com.github.rmcdouga.fitg.webapp.authentication;

public class InvalidAuthenticationTokenException extends RuntimeException {

    public InvalidAuthenticationTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}