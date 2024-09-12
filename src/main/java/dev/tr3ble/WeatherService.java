package dev.tr3ble;

import java.io.IOException;

public class WeatherService {
    private final JsonParser jsonParser;
    private String currentLocation = "London";

    public WeatherService(JsonParser jsonParser) {
        this.jsonParser = jsonParser;
    }

    public String getWeather() throws IOException {
        return jsonParser.parseWeather(currentLocation);
    }

    public void setLocation(String location) {
        this.currentLocation = location.trim();
    }

    public String getCurrentLocation() {
        return currentLocation;
    }
}