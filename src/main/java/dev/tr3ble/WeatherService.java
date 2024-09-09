package dev.tr3ble;

import java.io.IOException;

public class WeatherService {
    private final JsonParser jsonParser;

    public WeatherService(JsonParser jsonParser) {
        this.jsonParser = jsonParser;
    }

    public String getWeather() throws IOException {
        return jsonParser.parseWeather();
    }
}
