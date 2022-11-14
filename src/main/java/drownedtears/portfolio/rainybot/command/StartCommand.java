package drownedtears.portfolio.rainybot.command;

import drownedtears.portfolio.rainybot.service.SendBotMessageService;
import drownedtears.portfolio.rainybot.service.UserService;
import drownedtears.portfolio.rainybot.user.User;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Start {@link Command}
 */
public class StartCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final UserService userService;
    public final static String MESSAGE = "Hello! I'm <b>Rainy Bot</b>! " +
            "I will make weather forecasts especially for you in any city in the world\n" +
            "If I do something wrong, don't swear, because I'm just learning\n<b>Better " +
            "write to my author - @igiveyouallmypower</b>\n\n" +
            "To see a list of all commands, type /help";

    public final static String ALT_MESSAGE = "We have already started.\nTo see a list of all commands, type /help";

    public StartCommand(SendBotMessageService sendBotMessageService, UserService userService) {
        this.sendBotMessageService = sendBotMessageService;
        this.userService = userService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String name = update.getMessage().getChat().getUserName();
        if (userService.findByChatId(chatId).isPresent()) {
            User user = userService.findByChatId(chatId).get();
            user.setName(name);
            boolean wasInactive = user.getActive();
            user.setActive(true);
            userService.save(user);
            if (!wasInactive) {
                sendBotMessageService.sendMessage(chatId, MESSAGE);
            } else {
                sendBotMessageService.sendMessage(chatId, ALT_MESSAGE);
            }
        } else {
            User user = new User();
            user.setActive(true);
            user.setChatId(chatId);
            user.setName(name != null ? name : chatId);
            userService.save(user);
            sendBotMessageService.sendMessage(chatId, MESSAGE);
        }
    }
}
