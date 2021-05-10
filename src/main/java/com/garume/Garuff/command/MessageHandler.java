package com.garume.Garuff.command;

public interface MessageHandler {
    void send(String message, LogLevel level);
}
