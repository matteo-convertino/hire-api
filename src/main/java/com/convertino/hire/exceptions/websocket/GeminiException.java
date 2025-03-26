package com.convertino.hire.exceptions.websocket;

public class GeminiException extends RuntimeException {

    public GeminiException() {
        super("There was an error while the chatbot was generating the response.");
    }
}