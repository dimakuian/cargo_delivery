package com.epam.delivery.service;

import javax.servlet.http.HttpServletRequest;

public final class EmptyParameterChecker {
    private EmptyParameterChecker() {
    }

    public static boolean notEmpty(HttpServletRequest request, String parameterName) {
        return request.getParameter(parameterName) != null && !request.getParameter(parameterName).isEmpty();
    }
}
