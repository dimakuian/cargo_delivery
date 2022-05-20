package com.epam.delivery.web.command.adminCommand;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.InvoiceDao;
import com.epam.delivery.db.entities.Invoice;
import com.epam.delivery.service.MessageBuilder;
import com.epam.delivery.service.StringToTimestampConverter;
import com.epam.delivery.web.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

public class AdminInvoicesCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String PARAM_RECORDS_PER_PAGE = "recordPerPage";
    private static final int DEFAULT_RECORDS_PER_PAGE = 10;
    private static final String PARAM_PAGE_NUMBER = "page_number";
    private static final String PARAM_SORT = "sort";
    private static final String DEFAULT_SORT = "id ASC";
    private static final int DEFAULT_FROM_SUM = 0;
    private static final String PARAM_FROM_SUM = "fromSum";
    private static final String PARAM_TO_SUM = "toSum";
    private static final String PARAM_STATUS_ID = "statusID";
    private static final String PARAM_FROM_DATE = "fromDate";
    private static final String PARAM_TO_DATE = "toDate";

    /**
     * Execution method for command.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return Address to go once the command is executed.
     */

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("start command");

        String forward = Path.PAGE__ERROR_PAGE;
        String errorMessage;

        int page = 1;
        int recordsPerPage = DEFAULT_RECORDS_PER_PAGE;
        String sortType = DEFAULT_SORT;
        double fromSum = DEFAULT_FROM_SUM;
        double toSum;

        HttpSession session = request.getSession();
        try {

            if (request.getParameter(PARAM_PAGE_NUMBER) != null && !request.getParameter(PARAM_PAGE_NUMBER).isEmpty()) {
                page = Integer.parseInt(request.getParameter(PARAM_PAGE_NUMBER));
            }

            if (request.getParameter(PARAM_RECORDS_PER_PAGE) != null &&
                    !request.getParameter(PARAM_RECORDS_PER_PAGE).isEmpty()) {
                recordsPerPage = Integer.parseInt(request.getParameter(PARAM_RECORDS_PER_PAGE));
                logger.trace("Request parameter: recordPerPage --> " + recordsPerPage);
            }

            if (request.getParameter(PARAM_SORT) != null && !request.getParameter(PARAM_SORT).isEmpty()) {
                sortType = request.getParameter(PARAM_SORT);
                logger.trace("Request parameter: sort --> " + sortType);
            }

            ConnectionBuilder connectionBuilder = new ConnectionPool();
            InvoiceDao invoiceDao = new InvoiceDao(connectionBuilder);

            if (request.getParameter(PARAM_FROM_SUM) != null && request.getParameter(PARAM_TO_SUM) != null) {
                fromSum = Double.parseDouble(request.getParameter(PARAM_FROM_SUM));
                toSum = Double.parseDouble(request.getParameter(PARAM_TO_SUM));
            } else {
                toSum = invoiceDao.getMaxInvoiceSum();
                logger.trace("Found in DB: toSum --> " + toSum);
            }

            List<Invoice> allInvoices;
            int noOfRecords;
            int startPosition = (page - 1) * recordsPerPage;

            if (request.getParameter(PARAM_STATUS_ID) != null && !request.getParameter(PARAM_STATUS_ID).isEmpty() &&
                    request.getParameter(PARAM_FROM_DATE) != null && !request.getParameter(PARAM_FROM_DATE).isEmpty()
                    && request.getParameter(PARAM_TO_DATE) != null && !request.getParameter(PARAM_TO_DATE).isEmpty()) {

                String startDayStr = request.getParameter(PARAM_FROM_DATE);
                logger.trace("Request parameter: fromDate --> " + startDayStr);

                Timestamp startDay = StringToTimestampConverter.beginTimestampConverter(startDayStr);

                String endDayStr = request.getParameter(PARAM_TO_DATE);
                logger.trace("Request parameter: toDate --> " + endDayStr);

                Timestamp endDay = StringToTimestampConverter.endTimestampConverter(endDayStr);

                long statusID = Long.parseLong(request.getParameter(PARAM_STATUS_ID));
                logger.trace("Request parameter: statusID --> " + statusID);

                request.setAttribute("fromDate", startDayStr);
                logger.trace("Set the request attribute: fromDate --> " + startDayStr);

                request.setAttribute("toDate", endDayStr);
                logger.trace("Set the request attribute: toDate --> " + endDayStr);

                request.setAttribute("statusID", statusID);
                logger.trace("Set the request attribute: statusID --> " + statusID);

                allInvoices = invoiceDao.findAll(sortType, startPosition, recordsPerPage, fromSum, toSum, statusID,
                        startDay, endDay);

                noOfRecords = invoiceDao.getNoOfFilteredInvoices(fromSum, toSum, statusID, startDay, endDay);

            } else if (request.getParameter(PARAM_STATUS_ID) != null && !request.getParameter(PARAM_STATUS_ID).isEmpty()) {

                long statusID = Long.parseLong(request.getParameter(PARAM_STATUS_ID));

                allInvoices = invoiceDao.findAll(sortType, startPosition, recordsPerPage,
                        fromSum, toSum, statusID);

                request.setAttribute("statusID", statusID);
                logger.trace("Set the request attribute: statusID --> " + statusID);

                noOfRecords = invoiceDao.getNoOfFilteredInvoices(fromSum, toSum, statusID);

            } else if (request.getParameter(PARAM_FROM_DATE) != null && !request.getParameter(PARAM_FROM_DATE).isEmpty()
                    && request.getParameter(PARAM_TO_DATE) != null && !request.getParameter(PARAM_TO_DATE).isEmpty()) {

                String startDayStr = request.getParameter(PARAM_FROM_DATE);
                logger.trace("Request parameter: fromDate --> " + startDayStr);

                Timestamp startDay = StringToTimestampConverter.beginTimestampConverter(startDayStr);

                String endDayStr = request.getParameter(PARAM_TO_DATE);
                logger.trace("Request parameter: toDate --> " + endDayStr);

                Timestamp endDay = StringToTimestampConverter.endTimestampConverter(endDayStr);

                allInvoices = invoiceDao.findAll(sortType, startPosition, recordsPerPage,
                        fromSum, toSum, startDay, endDay);

                request.setAttribute("fromDate", startDayStr);
                logger.trace("Set the request attribute: fromDate --> " + startDayStr);

                request.setAttribute("toDate", endDayStr);
                logger.trace("Set the request attribute: toDate --> " + endDayStr);

                noOfRecords = invoiceDao.getNoOfFilteredInvoices(fromSum, toSum, startDay, endDay);

            } else {
                allInvoices = invoiceDao.findAll(sortType, startPosition, recordsPerPage, fromSum, toSum);
                noOfRecords = invoiceDao.getNoOfAllInvoices();
            }

            logger.trace("Found in DB: allInvoices --> " + allInvoices);
            logger.trace("Found in DB: noOfRecords --> " + noOfRecords);

            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

            request.setAttribute("noOfPages", noOfPages);
            logger.trace("Set the request attribute: noOfPages --> " + noOfPages);

            request.setAttribute("page", page);
            logger.trace("Set the request attribute: page --> " + page);

            request.setAttribute("allInvoices", allInvoices);
            logger.trace("Set the request attribute: allInvoices --> " + allInvoices);

            request.setAttribute("recordPerPage", recordsPerPage);
            logger.trace("Set the request attribute: recordPerPage --> " + recordsPerPage);

            request.setAttribute("sort", sortType);
            logger.trace("Set the request attribute: sort --> " + sortType);

            request.setAttribute("fromSum", fromSum);
            logger.trace("Set the request attribute: fromSum --> " + fromSum);

            request.setAttribute("toSum", toSum);
            logger.trace("Set the request attribute: toSum --> " + toSum);

            forward = Path.PAGE__ADMIN_INVOICES;

        } catch (NumberFormatException ex1) {
            errorMessage = MessageBuilder.getLocaleMessage(session, "error_message_number");
            request.setAttribute("message", errorMessage);
            logger.error("errorMessage --> " + errorMessage);
            logger.error("Exception --> " + ex1);
        } catch (Exception ex2) {
            errorMessage = MessageBuilder.getLocaleMessage(session, "error_message_unknown");
            request.setAttribute("message", errorMessage);
            logger.error("errorMessage --> " + errorMessage);
            logger.error("Exception --> " + ex2);
        }

        logger.debug("Command finished");
        return forward;
    }
}
