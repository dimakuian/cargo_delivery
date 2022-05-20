package com.epam.delivery.service;

import javax.servlet.http.HttpServletRequest;

public final class EmptyParameterChecker {
    private EmptyParameterChecker() {
    }

    /**
     * @param request HttpServletRequest
     * @param parameterName String name request for check
     * @return true if not empty
     */

    public static boolean notEmpty(HttpServletRequest request, String parameterName) {
        return request.getParameter(parameterName) != null && !request.getParameter(parameterName).isEmpty();
    }
}
