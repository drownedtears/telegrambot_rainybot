package drownedtears.portfolio.rainybot.command;

import drownedtears.portfolio.rainybot.service.SendBotMessageService;
import drownedtears.portfolio.rainybot.service.UserService;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Stat {@link Command}
 */
public class StatCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final UserService userService;

    public final static String MESSAGE = "Number of users is %s!\uD83C\uDF89";

    public StatCommand(SendBotMessageService sendBotMessageService, UserService userService) {
        this.sendBotMessageService = sendBotMessageService;
        this.userService = userService;
    }

    @Override
    public void execute(Update update) {
        int activeUserCount = userService.getAllActiveUsers().size();
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), String.format(MESSAGE, activeUserCount));
    }
}
