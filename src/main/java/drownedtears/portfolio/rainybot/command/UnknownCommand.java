package drownedtears.portfolio.rainybot.command;

import drownedtears.portfolio.rainybot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Unknown {@link Command}
 */
public class UnknownCommand implements Command {
    private final SendBotMessageService sendBotMessageService;

    public final static String MESSAGE = "I do not understand you\n" +
            "To see a list of all commands, type /help";

    public UnknownCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), MESSAGE);
    }
}
