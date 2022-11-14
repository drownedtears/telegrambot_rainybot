package drownedtears.portfolio.rainybot.command;

/**
 * Enumeration for {@link Command}
 */
public enum CommandName {
    START("/start"),
    HELP("/help"),
    NO("not-a-command"),
    STAT("/stat"),
    CITY("/city"),
    WEATHER("/weather"),

    TIME("/time"),
    STOP("/stop");

    private  final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
