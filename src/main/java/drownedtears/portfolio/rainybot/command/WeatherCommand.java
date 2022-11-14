package drownedtears.portfolio.rainybot.command;


import drownedtears.portfolio.rainybot.service.SendBotMessageService;
import drownedtears.portfolio.rainybot.service.UserService;
import drownedtears.portfolio.rainybot.user.User;
import drownedtears.portfolio.rainybot.util.WeatherData;
import org.json.JSONObject;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;


public class WeatherCommand implements  Command {
    private final SendBotMessageService sendBotMessageService;
    private final UserService userService;

    private final static String PREFIX_DAILY = "Hey, here is your daily weather forecast:\n\n";
    private final static String MESSAGE = "In the  ";
    private final static String LOADING = "Please, wait a second...";
    private final static String CITY_NOT_PRESENT = "It looks like you didn't choose city\n" +
            "Type <b>/city London</b>\nor\n" +
            "<b>/weather London</b>";
    private final static String ERROR = "Oops... I can't make a weather forecast for you\n" +
            "Please, check city name or try again later";

    private final static String ERROR_TIMED = "Oops... It's time for your weather forecast," +
            "but something went wrong\nPlease, try to get it by typing <b>/weather</b>";
    private final WeatherData weatherData;

    public WeatherCommand(SendBotMessageService sendBotMessageService, UserService userService) {
        this.sendBotMessageService = sendBotMessageService;
        this.userService = userService;
        this.weatherData = new WeatherData();
    }

    /**
     * gets weather from https://www.weatherapi.com/
     */
    private void getUrlContentFromWeatherAPI(String cityName, String chatId) throws IOException {
        StringBuilder content = new StringBuilder();
        String urlAddress = "https://api.weatherapi.com/v1/current.json?key=1416cc3a729f485f8b6154201220211&q=" + cityName + "&aqi=no";

        URL url = new URL(urlAddress);
        URLConnection urlConnection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

        String line;
        while((line = bufferedReader.readLine()) != null) {
            content.append(line).append("\n");
        }

        setWeatherData(content.toString(), chatId);
    }
    //todo декларативно? + more info
    private void setWeatherData(String content, String chatId) throws IOException {
        if (!content.isEmpty()) {
            JSONObject jsonObject = new JSONObject(content);
            weatherData.setDataJSON(jsonObject, chatId);
        } else {
            throw new IOException();
        }
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String message = update.getMessage().getText();
        String cityName = message.replace("/weather ", "");

        if (userService.findByChatId(chatId).isPresent()) {
            User user = userService.findByChatId(chatId).get();
            if (message.equals("/weather")) {
                if (user.getCity().equals("")) {
                    sendBotMessageService.sendMessage(chatId,
                            CITY_NOT_PRESENT);
                    return;
                }
                cityName = user.getCity();
            }
        }

        try {
            executionLoading(chatId);

            getUrlContentFromWeatherAPI(cityName, chatId);

            sendBotMessageService.sendMessage(chatId,
                    MESSAGE + wrapMessage(cityName) + " now:" + " \n" +
                            "Temperature " + weatherData.getTemp() + "\n" +
                            "Wind " + weatherData.getWind());

            sendBotMessageService.sendPhoto(weatherData.getPhoto());

        } catch (Exception e) {
            //todo logging
            sendBotMessageService.sendMessage(chatId, ERROR_TIMED);
        }
    }

    @Override
    public void execute(String chatId) {
        User user = userService.findByChatId(chatId).get();
        try {

            getUrlContentFromWeatherAPI(user.getCity(), chatId);

            sendBotMessageService.sendMessage(chatId, PREFIX_DAILY +
                    MESSAGE + wrapMessage(user.getCity()) + " now:" + " \n" +
                            "Temperature " + weatherData.getTemp() + "\n" +
                            "Wind " + weatherData.getWind());

            sendBotMessageService.sendPhoto(weatherData.getPhoto());

        } catch (Exception e) {
            //todo logging
            sendBotMessageService.sendMessage(chatId, ERROR);
        }
    }

    public void executionLoading(String chatId) {
        sendBotMessageService.sendMessage(chatId, LOADING);
    }
}
