package com.garume.Garuff.event.events.command;

import com.garume.Garuff.event.CancellableEvent;

public class ChatSendEvent extends CancellableEvent {
    private String message;

    public ChatSendEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}