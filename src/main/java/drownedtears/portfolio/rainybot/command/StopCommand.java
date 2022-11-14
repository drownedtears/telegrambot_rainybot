package drownedtears.portfolio.rainybot.command;

import drownedtears.portfolio.rainybot.service.SendBotMessageService;
import drownedtears.portfolio.rainybot.service.UserService;
import drownedtears.portfolio.rainybot.user.User;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Stop {@link Command}
 */
public class StopCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final UserService userService;

    public final static String MESSAGE = "Looks like it's time to say goodbye, hopefully not for long... ";

    public StopCommand(SendBotMessageService sendBotMessageService, UserService userService) {
        this.sendBotMessageService = sendBotMessageService;
        this.userService = userService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        if (userService.findByChatId(chatId).isPresent()) {
            User user = userService.findByChatId(chatId).get();
            boolean wasActive = user.getActive();
            user.setActive(false);
            userService.save(user);
            if (wasActive) {
                sendBotMessageService.sendMessage(chatId, MESSAGE);
            }
        }
    }
}
