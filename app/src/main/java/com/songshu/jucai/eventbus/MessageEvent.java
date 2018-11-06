package com.songshu.jucai.eventbus;

public class MessageEvent {


    private int type;

    private String message;

    private Object content;


    public MessageEvent() {
    }

    public MessageEvent(int type) {
        this.type = type;
    }

    public MessageEvent(String message) {
        this.message = message;
    }

    public MessageEvent(String message, Object content) {
        this.message = message;
        this.content = content;
    }

    public MessageEvent(int type, String message) {
        this.type = type;
        this.message = message;
    }


    public MessageEvent(int type, Object content) {
        this.type = type;
        this.content = content;
    }


    public MessageEvent(int type, Object content, String message) {
        this.type = type;
        this.message = message;
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
