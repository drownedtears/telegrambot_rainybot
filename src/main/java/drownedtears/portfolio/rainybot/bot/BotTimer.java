package drownedtears.portfolio.rainybot.bot;

import drownedtears.portfolio.rainybot.user.User;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BotTimer implements Runnable {

    private final RainyBot telegramBot;

    public BotTimer(RainyBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    /**
     * Sends weather forecast for all users who have time = now()
     *
     * timer at first every millisecond checks if time now is like hh:mm:00
     * than finding all users with this time and sends weather forecast
     * than sleeps for 30 sec and again...
     */
    @Override
    public void run() {
        int t = 1;
        while(true){
            try {
                Thread.sleep(t);
                t = 1;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String timePattern = "HH:mm";
            ZonedDateTime curTime = ZonedDateTime.now(ZoneId.of( "Europe/Moscow"));
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(timePattern);
            if(curTime.getSecond() == 0) {
                t = 1000 * 30;
                List<User> list = telegramBot.getUserService().getAllUsersByTime(curTime.format(dateTimeFormatter));
                for (User user : list) {
                    telegramBot.getCommandContainer().getCommand("/weather").execute(user.getChatId());
                }
            }
        }
    }
}
