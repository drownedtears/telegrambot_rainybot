package drownedtears.portfolio.rainybot.util;

import lombok.Data;
import org.json.JSONObject;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

@Data
@Singleton
public class WeatherData {

    private String temp;
    private String wind;
    private String wind_dir;
    private String wind_kph;
    private SendPhoto photo;
    @Inject
    public WeatherData() { }

    public void setDataJSON(JSONObject jsonObject, String chatId) {
        setTempJSON(jsonObject);
        setWind_kphJSON(jsonObject);
        setWind_dirJSON(jsonObject);
        setWindJSON(jsonObject);
        setPhotoJSON(jsonObject, chatId);
    }

    public void setTempJSON(JSONObject jsonObject) {
        if (jsonObject.getJSONObject("current").getDouble("temp_c") < 0) {
            setTemp(wrapText(Math.abs(jsonObject.getJSONObject("current").getDouble("temp_c")) + " °C" + " below zero"));
        } else {
            setTemp(wrapText(Math.abs(jsonObject.getJSONObject("current").getDouble("temp_c")) + " °C" + " above zero"));
        }
    }

    public void setWind_kphJSON(JSONObject jsonObject) {
        setWind_kph(" " + jsonObject.getJSONObject(("current")).getDouble("wind_kph"));
    }

    public void setWind_dirJSON(JSONObject jsonObject) {
        if (Objects.equals(jsonObject.getJSONObject(("current")).getString("wind_dir"), "S")) {
            setWind_dir("north");
        } else if (Objects.equals(jsonObject.getJSONObject(("current")).getString("wind_dir"), "N")) {
            setWind_dir("south");
        } else if (Objects.equals(jsonObject.getJSONObject(("current")).getString("wind_dir"), "W")) {
            setWind_dir("east");
        } else {
            setWind_dir("west");
        }
    }

    public void setWindJSON(JSONObject jsonObject) {
        setWind(wrapText(getWind_dir() + getWind_kph()+ " k/h"));
    }

    public void setPhotoJSON(JSONObject jsonObject, String chatId) {
        try (BufferedInputStream in = new BufferedInputStream(
                new URL("http:" + jsonObject.getJSONObject("current").getJSONObject("condition").getString("icon")).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("weatherPicture.png")) {

            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            // handle exception
        }
        setPhoto(new SendPhoto(chatId, new InputFile(new File("weatherPicture.png"))));
    }

    public String wrapText(String text) {
        return "<b>" + text + "</b>";
    }

}
