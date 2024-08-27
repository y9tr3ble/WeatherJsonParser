package dev.tr3ble;

import java.io.IOException;

public class PlayerDataService {
    private final JsonParser jsonParser;

    public PlayerDataService(JsonParser jsonParser) {
        this.jsonParser = jsonParser;
    }

    public Integer getPlayersNum() throws IOException {
        return jsonParser.parsePlayersNum();
    }
}
