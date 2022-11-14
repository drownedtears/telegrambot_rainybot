package drownedtears.portfolio.rainybot.command;

import drownedtears.portfolio.rainybot.service.SendBotMessageService;
import drownedtears.portfolio.rainybot.service.UserService;
import drownedtears.portfolio.rainybot.user.User;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CityCommand implements Command{
    private final SendBotMessageService sendBotMessageService;
    private final UserService userService;

    public final static String MESSAGE = "Changed your city to ";
    public final static String WRONG_MESSAGE = "I think you forgot to type a city, try again like this:\n<b>/city London</b>";

    public CityCommand(SendBotMessageService sendBotMessageService, UserService userService) {
        this.sendBotMessageService = sendBotMessageService;
        this.userService = userService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String cityName = update.getMessage().getText().replace("/city ", "");
        if (cityName.equals("/city")) {
            sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), WRONG_MESSAGE);
        } else {
            if (userService.findByChatId(chatId).isPresent()) {
                User user = userService.findByChatId(chatId).get();
                user.setCity(cityName);
                userService.save(user);
            }
            sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), MESSAGE + wrapMessage(cityName));
        }
    }
}
