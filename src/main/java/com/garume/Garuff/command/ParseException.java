package com.garume.Garuff.command;

import com.garume.Garuff.command.parser.TypeParser;
import java.util.Stack;

/**
 * This is an exception thrown by {@link TypeParser} when {@link TypeParser#parse(Stack)} failed.
 */
public class ParseException extends Exception {

    public ParseException(String message) {
        super(message);
    }
}
