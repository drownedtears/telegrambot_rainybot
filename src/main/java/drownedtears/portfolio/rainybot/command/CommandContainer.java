package drownedtears.portfolio.rainybot.command;

import com.google.common.collect.ImmutableMap;
import drownedtears.portfolio.rainybot.service.SendBotMessageService;
import drownedtears.portfolio.rainybot.service.UserService;

import static drownedtears.portfolio.rainybot.command.CommandName.*;

public class CommandContainer {
    private final ImmutableMap<String, Command> commandContainer;
    private final Command unknownCommand;

    public CommandContainer(SendBotMessageService sendBotMessageService, UserService userService) {
        commandContainer = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(sendBotMessageService, userService))
                .put(STOP.getCommandName(), new StopCommand(sendBotMessageService, userService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(NO.getCommandName(), new NoCommand(sendBotMessageService))
                .put(STAT.getCommandName(), new StatCommand(sendBotMessageService, userService))
                .put(CITY.getCommandName(), new CityCommand(sendBotMessageService, userService))
                .put(WEATHER.getCommandName(), new WeatherCommand(sendBotMessageService, userService))
                .put(TIME.getCommandName(), new TimeCommand(sendBotMessageService, userService))
                .build();
        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command getCommand(String commandName) {
        if (commandName.startsWith("/city ")) {
            return commandContainer.get("/city");
        } else if (commandName.startsWith("/weather ")) {
            return commandContainer.get("/weather");
        } else if (commandName.startsWith("/time ")) {
            return commandContainer.get("/time");
        }
        return commandContainer.getOrDefault(commandName, unknownCommand);
    }
}
