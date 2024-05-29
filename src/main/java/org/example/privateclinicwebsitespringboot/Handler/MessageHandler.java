package org.example.privateclinicwebsitespringboot.Handler;

public class MessageHandler {

    private String messageType;
    private String message;
    public MessageHandler() {
    }

    public MessageHandler(String messageType, String message) {
        this.messageType = messageType;
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
