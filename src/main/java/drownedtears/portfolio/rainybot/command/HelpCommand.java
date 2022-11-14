package drownedtears.portfolio.rainybot.command;

import drownedtears.portfolio.rainybot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static drownedtears.portfolio.rainybot.command.CommandName.*;

/**
 * Help {@link Command}
 */
public class HelpCommand implements Command {
    private final SendBotMessageService sendBotMessageService;

    public final static String MESSAGE = String.format("<b>Accessible commands:</b>\n\n"
                    + "%s - get started with me\n"
                    + "%s - see statistics\n"
                    + "%s - set the city\n"
                    + "%s - get information about the weather in your city\n"
                    + "%s <b>London</b> - get information about the weather in London\n"
                    + "%s - stop working with me\n"
                    + "%s <b>hh:mm</b> - set time for daily forecast in your city\n\n"
                    + "%s - get help with me\n",
            START.getCommandName(), STAT.getCommandName(), CITY.getCommandName(),
            WEATHER.getCommandName(), WEATHER.getCommandName(), STOP.getCommandName(),
            TIME.getCommandName(), HELP.getCommandName());

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), MESSAGE);
    }
}
