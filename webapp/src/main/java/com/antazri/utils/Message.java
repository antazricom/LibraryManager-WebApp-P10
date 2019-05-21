package com.antazri.utils;

import java.util.ResourceBundle;

public class Message {

    private static ResourceBundle text = ResourceBundle.getBundle("/com/antazri/webapp/utils/messages");

    public static ResourceBundle getText() {
        return text;
    }
}
