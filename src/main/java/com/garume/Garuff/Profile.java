package com.garume.Garuff;


import com.garume.Garuff.command.MessageHandler;

public interface Profile {

    char getPrefix();

    void setPrefix(char prefix);

    MessageHandler getMessageHandler();

    void load();

    void save();

}
