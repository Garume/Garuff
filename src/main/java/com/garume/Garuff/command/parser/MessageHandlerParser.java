package com.garume.Garuff.command.parser;

import com.garume.Garuff.Garuffhub;
import com.garume.Garuff.command.MessageHandler;
import com.garume.Garuff.command.ParseException;


import java.util.Stack;

public class MessageHandlerParser implements TypeParser<MessageHandler> {
    @Override
    public MessageHandler parse(int index, Stack<String> args) throws ParseException {
        return Garuffhub.getProfile().getMessageHandler();
    }

    @Override
    public String getToken() {
        return "message";
    }

    @Override
    public Class<MessageHandler> getType() {
        return MessageHandler.class;
    }
}
