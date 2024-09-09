package dev.tr3ble;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class JsonParser {

    private final OkHttpClient client = new OkHttpClient();
    private static final String API_KEY = "API_KEY";
    private static final String URL = "http://api.weatherapi.com/v1/current.json?key=" + API_KEY + "&q=London&aqi=no";

    public JsonObject fetchJsonData() throws IOException {
        Request request = new Request.Builder().url(URL).build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String jsonString = response.body().string();
                return new Gson().fromJson(jsonString, JsonObject.class);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при выполнении HTTP-запроса: " + e.getMessage());
        }
        return null;
    }

    public String parseWeather() throws IOException {
        JsonObject jsonObject = fetchJsonData();
        if (jsonObject != null && jsonObject.has("location")) {
            JsonObject playersObject = jsonObject.getAsJsonObject("location");
            if (playersObject.has("name")) {
                return playersObject.get("name").getAsString();
            }
        }
        return null;
    }
}
