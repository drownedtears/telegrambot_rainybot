package drownedtears.portfolio.rainybot.service;

import drownedtears.portfolio.rainybot.bot.RainyBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Implementation for {@link SendBotMessageService}
 */
@Service
public class SendBotMessageServiceImpl implements SendBotMessageService{

    private final RainyBot telegramBot;

    @Autowired
    public SendBotMessageServiceImpl(RainyBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void sendMessage(String chatId, String message) {
        SendMessage sm = new SendMessage();
        sm.setChatId(chatId);
        sm.setText(message);
        sm.enableHtml(true);

        try {
            telegramBot.execute(sm);
        } catch (TelegramApiException e) {
            //todo logging
            e.printStackTrace();
        }
    }


    @Override
    public void sendPhoto(SendPhoto photo) {
        try {
            telegramBot.execute(photo);
        } catch (TelegramApiException e) {
            //todo logging
            e.printStackTrace();
        }
    }
}
