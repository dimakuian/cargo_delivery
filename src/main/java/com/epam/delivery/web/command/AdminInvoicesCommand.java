package com.epam.delivery.web.command;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.InvoiceDao;
import com.epam.delivery.db.entities.Invoice;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AdminInvoicesCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String PARAM_RECORDS_PER_PAGE = "recordPerPage";
    private static final int DEFAULT_RECORDS_PER_PAGE = 10;
    private static final String PARAM_PAGE_NUMBER = "page_number";
    private static final String PARAM_SORT = "sort";
    private static final String DEFAULT_SORT = "id ASC";
    public static final int DEFAULT_FROM_SUM = 0;
    public static final String PARAM_FROM_SUM = "fromSum";
    public static final String PARAM_TO_SUM = "toSum";
    public static final String PARAM_STATUS_ID = "statusID";

    /**
     * Execution method for command.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return Address to go once the command is executed.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("start command");

        String forward = Path.PAGE__ERROR_PAGE;
        String errorMessage;
        ServletContext context = request.getServletContext();

        int page = 1;
        int recordsPerPage = DEFAULT_RECORDS_PER_PAGE;
        String sortType = DEFAULT_SORT;
        double fromSum = DEFAULT_FROM_SUM;
        double toSum;

        System.out.println(request.getParameter("fromDate"));
        System.out.println(request.getParameter("toDate"));


        try {

            if (request.getParameter(PARAM_PAGE_NUMBER) != null) {
                page = Integer.parseInt(request.getParameter(PARAM_PAGE_NUMBER));
            }

            if (request.getParameter(PARAM_RECORDS_PER_PAGE) != null) {
                recordsPerPage = Integer.parseInt(request.getParameter(PARAM_RECORDS_PER_PAGE));
                logger.trace("Request parameter: recordPerPage --> " + recordsPerPage);
            }

            if (request.getParameter(PARAM_SORT) != null) {
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

            if (request.getParameter(PARAM_STATUS_ID) != null && !request.getParameter(PARAM_STATUS_ID).isEmpty()) {
                long statusID = Long.parseLong(request.getParameter(PARAM_STATUS_ID));
                allInvoices = invoiceDao.findAll(sortType, (page - 1) * recordsPerPage, recordsPerPage, fromSum, toSum, statusID);

                request.setAttribute("statusID", statusID);
                logger.trace("Set the request attribute: statusID --> " + statusID);

                noOfRecords = invoiceDao.getNoOfAllInvoices(statusID);

            } else {
                allInvoices = invoiceDao.findAll(sortType, (page - 1) * recordsPerPage, recordsPerPage, fromSum, toSum);
                logger.trace("Found in DB: allInvoices --> " + allInvoices);
                noOfRecords = invoiceDao.getNoOfAllInvoices();
            }


            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);

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

        } catch (Exception exception) {
            errorMessage = "can't read all invoices"; // replace
            context.setAttribute("message", errorMessage);
            logger.error("errorMessage --> " + errorMessage);
            logger.error("Exception --> " + exception);
        }

        logger.debug("Command finished");
        return forward;
    }
}
