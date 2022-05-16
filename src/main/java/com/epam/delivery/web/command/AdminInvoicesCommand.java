package com.epam.delivery.web.command;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.InvoiceDao;
import com.epam.delivery.db.entities.Invoice;
import com.epam.delivery.service.sort.InvoiceOrderByService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminInvoicesCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    public static final String PARAM_RECORDS_PER_PAGE = "recordPerPage";
    public static final int DEFAULT_RECORDS_PER_PAGE = 10;
    public static final String PARAM_PAGE_NUMBER = "page_number";
    public static final String PARAM_SORT = "sort";

    /**
     * Execution method for command.
     *
     * @param request
     * @param response
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


        try {
            if (request.getParameter(PARAM_PAGE_NUMBER) != null)
                page = Integer.parseInt(request.getParameter(PARAM_PAGE_NUMBER));

            if (request.getParameter(PARAM_RECORDS_PER_PAGE) != null) {
                recordsPerPage = Integer.parseInt(request.getParameter(PARAM_RECORDS_PER_PAGE));
                logger.trace("Request parameter: recordPerPage --> " + recordsPerPage);
            }

            ConnectionBuilder connectionBuilder = new ConnectionPool();
            InvoiceDao invoiceDao = new InvoiceDao(connectionBuilder);
            List<Invoice> allInvoices = new ArrayList<>(invoiceDao.findAll((page - 1) * recordsPerPage, recordsPerPage));
            logger.trace("Found in DB: allInvoices --> " + allInvoices);

            if (request.getParameter(PARAM_SORT) != null) {
                String sortType = request.getParameter(PARAM_SORT);
                logger.trace("Request parameter: sort --> " + sortType);

                allInvoices = new InvoiceOrderByService().sort(sortType, allInvoices);

                context.setAttribute("sort", sortType);
                logger.trace("Set the session attribute: sort --> " + sortType);
            }

            int noOfRecords = invoiceDao.getNoOfAllInvoices();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

            context.setAttribute("noOfPages", noOfPages);
            context.setAttribute("currentPage", page);

            context.setAttribute("allInvoices", allInvoices);
            logger.trace("Set the session attribute: allInvoices --> " + allInvoices);

            context.setAttribute("recordPerPage", recordsPerPage);
            logger.trace("Set the session attribute: recordPerPage --> " + recordsPerPage);

            forward = Path.PAGE__ADMIN_INVOICES;

        } catch (Exception exception) {
            errorMessage = "can't read all invoices"; // replace
            context.setAttribute("message", errorMessage);
            logger.error("errorMessage --> " + errorMessage);
            logger.error("Exception --> " + exception.getMessage());
        }

        logger.debug("Command finished");
        return forward;
    }
}
