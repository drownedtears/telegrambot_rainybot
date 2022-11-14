package drownedtears.portfolio.rainybot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

/**
 * Service for sending messages via bot
 */
public interface SendBotMessageService {
    /**
     * @param chatId where to send message
     * @param message message text
     */
    void sendMessage(String chatId, String message);

    void sendPhoto(SendPhoto photo);
}
