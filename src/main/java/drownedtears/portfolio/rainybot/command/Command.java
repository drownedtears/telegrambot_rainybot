package drownedtears.portfolio.rainybot.command;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * For handling bots commands
 */
public interface Command {
    /**
     * @param update is param with all the needed data for executing
     */
    void execute(Update update);

    /**
     * for sending message without any commands/messages received
     * @param chatId chatId of user we want to sent message
     * realization only for {@link WeatherCommand}
     */
    default void execute(String chatId) {

    }

    /**
     * @return (<b>message</b>).toString()
     */
    default String wrapMessage(String message) {
        return "<b>" + message + "</b>";
    }

}
