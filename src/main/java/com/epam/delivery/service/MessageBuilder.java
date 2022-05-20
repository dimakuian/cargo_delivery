package com.epam.delivery.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.ResourceBundle;

public final class MessageBuilder {

    private static final String PARAM_LOCALE = "locale";
    private static final String PROPERTY_NAME = "resource";

    private MessageBuilder() {
    }

    /**
     * @param session HttpSession session
     * @param key key for resources bundle
     * @return localization message
     */

    public static String getLocaleMessage (HttpSession session, String key) {
        String locale = (String) session.getAttribute(PARAM_LOCALE);
        Locale current = new Locale(locale, "");
        ResourceBundle resourceBundle = ResourceBundle.getBundle(PROPERTY_NAME,current);
        return resourceBundle.getString(key);
    }
}
