package drownedtears.portfolio.rainybot.bot;

import drownedtears.portfolio.rainybot.command.CommandContainer;
import drownedtears.portfolio.rainybot.service.SendBotMessageService;
import drownedtears.portfolio.rainybot.service.SendBotMessageServiceImpl;
import drownedtears.portfolio.rainybot.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;


import static drownedtears.portfolio.rainybot.command.CommandName.*;

/**
 * TelegramBot for VK by drownedtears (https://t.me/igiveyouallmypower)
 */
@Component
public class RainyBot extends TelegramLongPollingBot { //todo все названия
//todo все логирование через аспекты
    private final String PREFIX = "/";
    private final Thread timer;
    private final CommandContainer commandContainer;
    private final SendBotMessageService sendBotMessageService;
    private final UserService userService;

    public RainyBot(UserService userService) {
        this.sendBotMessageService = new SendBotMessageServiceImpl(this);
        this.userService = userService;
        this.commandContainer = new CommandContainer(sendBotMessageService, this.userService);

        //todo rework
        timer = new Thread(new BotTimer(this));
        timer.start();
    }

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    /**
     * receiving messages from user and handling with then
     * @param update consist all the needed info of message
     */
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();

            System.out.println(message); //todo change to logging + time of msg

            Boolean isActive = false;
            if (userService.findByChatId(update.getMessage().getChatId().toString()).isPresent()) {
                isActive = userService.findByChatId(update.getMessage().getChatId().toString()).get().getActive();
            }

            if (message.toLowerCase().equals(START.getCommandName())) {
                commandContainer.getCommand(START.getCommandName()).execute(update);
            } else if (isActive) {
                if (message.startsWith(PREFIX)) {
                    String commandName = message.toLowerCase();
                    commandContainer.getCommand(commandName).execute(update);
                } else {
                    commandContainer.getCommand(NO.getCommandName()).execute(update);
                }
            }
            //todo command for weather
        }
    }

    public UserService getUserService() {
        return userService;
    }

    public CommandContainer getCommandContainer() {
        return commandContainer;
    }

    /**
     * sends weather at 07:00:00, 14:00:00 and 19:00:00 every day to all Active {@link User}
     *
     * gets weather from https://www.weatherapi.com/
     */
    public void sendWeather() {
        //https://api.weatherapi.com/v1/current.json?key=1416cc3a729f485f8b6154201220211&q=Moscow&aqi=no

        //todo sendweather to all users
    }

}
