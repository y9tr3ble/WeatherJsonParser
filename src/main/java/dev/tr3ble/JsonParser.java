package dev.tr3ble;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class JsonParser {

    private final OkHttpClient client = new OkHttpClient();
    private static final String URL = "https://obsidian-mc.ru/api/monitoring/v1/servers/3yRp2q5QG2/status/d34.gamely.pro:20712";

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

    public Integer parsePlayersNum() throws IOException {
        JsonObject jsonObject = fetchJsonData();
        if (jsonObject != null && jsonObject.has("players")) {
            JsonObject playersObject = jsonObject.getAsJsonObject("players");
            if (playersObject.has("num")) {
                return playersObject.get("num").getAsInt();
            }
        }
        return null;
    }
}
