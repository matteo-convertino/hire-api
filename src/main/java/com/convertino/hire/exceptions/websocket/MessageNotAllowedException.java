package com.convertino.hire.exceptions.websocket;

public class MessageNotAllowedException extends RuntimeException {

    public MessageNotAllowedException() {
        super("Send message to a completed interview is not allowed.");
    }
}