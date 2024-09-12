package dev.tr3ble;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class JsonParser {

    private final OkHttpClient client = new OkHttpClient();
    private static final String API_KEY = "c725ce4ef9ab44bdacb132249240909";
    private static final String URL = "http://api.weatherapi.com/v1/current.json?key=" + API_KEY + "&aqi=no&q=";

    public JsonObject fetchJsonData(String location) throws IOException {
        String url = URL + location;
        Request request = new Request.Builder().url(url).build();
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

    public String parseWeather(String location) throws IOException {
        JsonObject jsonObject = fetchJsonData(location);
        if (jsonObject != null && jsonObject.has("location")) {
            JsonObject locationObject = jsonObject.getAsJsonObject("location");
            JsonObject currentObject = jsonObject.getAsJsonObject("current");
            String temperature = currentObject.get("temp_c").getAsString();
            String condition = currentObject.getAsJsonObject("condition").get("text").getAsString();
            String name = locationObject.get("name").getAsString();
            return name + " " + temperature + "°C " + condition;
        }
        return null;
    }
}
