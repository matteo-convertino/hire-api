package com.convertino.hire.exceptions.websocket;

public class UnauthorizedSubscriptionException extends RuntimeException {

    public UnauthorizedSubscriptionException(String topic) {
        super(String.format("Subscription to topic '%s' is not allowed.", topic));
    }
}