package com.epam.delivery.command;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {

    private static final Map<String, Command> commands = new TreeMap<>();

    static {
        // common commands
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("enterRegistrationForm", new EnterRegistrationCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("countFare", new CountFareCommand());
        commands.put("enterCountFare", new EnterCountFareCommand());
        commands.put("setLocale", new SetLocaleCommand());

        // client commands
        commands.put("userCabinet", new UserCabinetCommand());
        commands.put("editUser", new EditUserCommand());
        commands.put("recharge", new RechargeCommand());
        commands.put("payOrder", new PayOrderCommand());
        commands.put("editOrder", new EditOrderCommand());
        commands.put("createOrder", new CreateOrderCommand());
        commands.put("enterCreateOrderForm", new EnterCreateOrderCommand());

        // admin commands
    }

    /**
     * Returns command object with the given name.
     *
     * @param commandName Name of the command.
     * @return Command object.
     */

    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            System.out.println("Command not found, name --> " + commandName); //replace to logger
            return commands.get("noCommand");
        }

        return commands.get(commandName);
    }

}
