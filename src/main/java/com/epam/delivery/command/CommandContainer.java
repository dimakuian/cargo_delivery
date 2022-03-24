package com.epam.delivery.command;

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

        // client commands
        commands.put("userCabinet", new UserCabinetCommand());
        commands.put("editUser", new EditUserCommand());
        commands.put("recharge", new RechargeCommand());
        commands.put("payOrder", new PayOrderCommand());
        commands.put("editOrder", new EditOrderCommand());
        commands.put("createOrder", new CreateOrderCommand());
        commands.put("viewCreateOrderPage", new ViewCreateOrderPageCommand());

        // admin commands

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
