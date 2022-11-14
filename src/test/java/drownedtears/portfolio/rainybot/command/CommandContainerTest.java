package drownedtears.portfolio.rainybot.command;

import drownedtears.portfolio.rainybot.service.UserService;
import drownedtears.portfolio.rainybot.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import drownedtears.portfolio.rainybot.service.SendBotMessageService;
import drownedtears.portfolio.rainybot.service.SendBotMessageServiceImpl;
import org.mockito.Mockito;

@DisplayName("Unit-level testing for CommandContainer")
public class CommandContainerTest {
    private CommandContainer commandContainer;

    @BeforeEach
    public void init() {
        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageServiceImpl.class);
        UserService userService = Mockito.mock(UserServiceImpl.class);
        commandContainer = new CommandContainer(sendBotMessageService, userService);
    }

    @Test
    public void shouldReturnUnknownCommand() {
        //given
        String unknownCommand = "/stsdfgewr";

        //when
        Command command = commandContainer.getCommand(unknownCommand);

        //then
        Assertions.assertEquals(UnknownCommand.class, command.getClass());
    }

    @Test
    public void shouldReturnStartCommand() {
        //given
        String startCommand = "/start";

        //when
        Command command = commandContainer.getCommand(startCommand);

        //then
        Assertions.assertEquals(StartCommand.class, command.getClass());
    }

    @Test
    public void shouldReturnStopCommand() {
        //given
        String stopCommand = "/stop";

        //when
        Command command = commandContainer.getCommand(stopCommand);

        //then
        Assertions.assertEquals(StopCommand.class, command.getClass());
    }
}
