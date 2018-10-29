package com.github.rmcdouga.fitg.webapp.authentication;

public class InvalidAuthenticationTokenException extends Exception {

    public InvalidAuthenticationTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}