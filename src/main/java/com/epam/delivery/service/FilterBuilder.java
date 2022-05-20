package com.epam.delivery.service;

import javax.servlet.http.HttpServletRequest;

import static com.epam.delivery.service.EmptyParameterChecker.notEmpty;


public class FilterBuilder {

    private static final String PARAM_STATUS_ID = "statusID";
    private static final String PARAM_FROM_DATE = "fromDate";
    private static final String PARAM_TO_DATE = "toDate";
    private static final String PARAM_DELIVERY_ADDRESS = "delivery_address";
    private static final String PARAM_SHIPPING_ADDRESS = "shipping_address";


    /**
     * @param request HttpServletRequest request
     * @return String presentation of filter
     */
    public static String buildFilter(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        if (notEmpty(request, PARAM_STATUS_ID)) {
            sb.append("AND o.shipping_status_id =").append(request.getParameter(PARAM_STATUS_ID)).append(" ");
        }
        if (notEmpty(request, PARAM_SHIPPING_ADDRESS)) {
            sb.append("AND o.shipping_address = ").append(request.getParameter(PARAM_SHIPPING_ADDRESS)).append(" ");
        }
        if (notEmpty(request, PARAM_DELIVERY_ADDRESS)) {
            sb.append("AND o.delivery_address =").append(request.getParameter(PARAM_DELIVERY_ADDRESS)).append(" ");
        }

        if (notEmpty(request, PARAM_FROM_DATE) && notEmpty(request, PARAM_TO_DATE)) {
            String startDayStr = request.getParameter(PARAM_FROM_DATE);
            String endDate = request.getParameter(PARAM_TO_DATE);
            String format = String.format("AND (DATE(creation_time) BETWEEN '%s 00:00:00' AND '%s 23:59:59' )",
                    startDayStr, endDate);
            sb.append(format);
        }
        return sb.toString();
    }
}
