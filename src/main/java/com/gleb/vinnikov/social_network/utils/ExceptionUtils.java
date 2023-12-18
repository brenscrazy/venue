package com.gleb.vinnikov.social_network.utils;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.function.Supplier;

public class ExceptionUtils {

    public static String retrieveErrorMessage(String messageProperty) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages");
        return bundle.getString(messageProperty);
    }

    public static IllegalArgumentException buildIllegalArgumentException(
            String messageProperty,
            Object... args) {
        String message = retrieveErrorMessage(messageProperty);
        return new IllegalArgumentException(MessageFormat.format(message, args));
    }

}
