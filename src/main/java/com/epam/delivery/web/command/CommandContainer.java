package com.epam.delivery.web.command;

import com.epam.delivery.web.command.adminCommand.*;
import com.epam.delivery.web.command.clientCommand.*;
import com.epam.delivery.web.command.common.ChangeUserPasswordCommand;
import com.epam.delivery.web.command.common.LogoutCommand;
import com.epam.delivery.web.command.common.NoCommand;
import com.epam.delivery.web.command.outOfControl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {
    private static final Logger logger = LogManager.getLogger();

    private static final Map<String, Command> commands = new TreeMap<>();

    static {
        // common commands
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("viewRegistrationPage", new ViewRegistrationPageCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("calculateCost", new CalculateCoastCommand());
        commands.put("viewCalculateCost", new ViewCalculateCostPageCommand());
        commands.put("setLocale", new SetLocaleCommand());
        commands.put("noCommand", new NoCommand());
        commands.put("changePassword",new ChangeUserPasswordCommand());

        // client commands
        commands.put("clientOrders", new ClientOrdersCommand());
        commands.put("editClient", new EditClientCommand());
        commands.put("recharge", new RechargeCommand());
        commands.put("payInvoice", new PayInvoiceCommand());
        commands.put("editOrder", new EditOrderCommand());
        commands.put("createOrder", new CreateOrderCommand());
        commands.put("viewCreateOrderPage", new ViewCreateOrderPageCommand());
        commands.put("clientViewOrder",new ClientViewOrderCommand());
        commands.put("clientViewInvoices",new ClientInvoicesCommand());
        commands.put("viewClientPage",new ViewClientPageCommand());

        // admin commands
        commands.put("adminOrders", new AdminOrdersCommand());
        commands.put("confirmOrder",new ConfirmOrderCommand());
        commands.put("changeOrderStatus",new ChangeOrderStatusCommand());
        commands.put("adminViewOrder",new AdminViewOrderCommand());
        commands.put("editAdmin",new EditAdminCommand());
        commands.put("adminInvoices",new AdminInvoicesCommand());
        commands.put("viewRegNewAdmin", new ViewRegNewAdminPageCommand());
        commands.put("viewAdminPage", new ViewAdminPageCommand());
        commands.put("regNewAdmin",new RegNewAdminCommand());


        logger.debug("Command container was successfully initialized");
        logger.trace("Number of commands --> " + commands.size());

    }

    /**
     * Returns command object with the given name.
     *
     * @param commandName Name of the command.
     * @return Command object.
     */

    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            logger.info("Command not found, name --> " + commandName);
            return commands.get("noCommand");
        }
        return commands.get(commandName);
    }
}
