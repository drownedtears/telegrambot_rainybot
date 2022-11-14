package drownedtears.portfolio.rainybot.command;

import drownedtears.portfolio.rainybot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * No {@link Command}
 */
public class NoCommand implements Command {
    private final SendBotMessageService sendBotMessageService;

    public final static String MESSAGE = "I only support commands that start with <b>'/'</b>" +
            "To see a list of all commands, type /help";

    public NoCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), MESSAGE);
    }
}
