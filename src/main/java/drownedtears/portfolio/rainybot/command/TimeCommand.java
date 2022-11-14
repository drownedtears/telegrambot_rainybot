package drownedtears.portfolio.rainybot.command;

import drownedtears.portfolio.rainybot.service.SendBotMessageService;
import drownedtears.portfolio.rainybot.service.UserService;
import drownedtears.portfolio.rainybot.user.User;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TimeCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final UserService userService;
    public final static String MESSAGE = "All right, I will send you weather forecast everyday at ";
    public final static String ALT_MESSAGE = "Please, type like this:\n" +
            "<b>/time HH:MM</b>";

    public final static String NO_CITY = "You must choose city at first, type <b>/city London</b>";

    public TimeCommand(SendBotMessageService sendBotMessageService, UserService userService) {
        this.sendBotMessageService = sendBotMessageService;
        this.userService = userService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String message = update.getMessage().getText().replace("/time ", "");
        if (userService.findByChatId(chatId).isPresent()) {
            User user = userService.findByChatId(chatId).get();
            if (!message.equals("/time")) {
                if (user.getCity().equals("")) {
                    sendBotMessageService.sendMessage(chatId, NO_CITY);
                } else {
                    if (message.matches("(?:[0-1]\\d|2[0-3]):([0-5]\\d)")) {
                        user.setTime(message);
                        userService.save(user);
                        sendBotMessageService.sendMessage(chatId, MESSAGE + wrapMessage(message));
                    } else {
                        sendBotMessageService.sendMessage(chatId, ALT_MESSAGE);
                    }
                }
            } else {
                sendBotMessageService.sendMessage(chatId, ALT_MESSAGE);
            }
        }
    }
}
