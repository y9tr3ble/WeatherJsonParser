package dev.tr3ble;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class TelegramAPI {

    public static void tgAPI(String[] args) {
        try {
            String botToken = "7482370857:AAHndhWhcSOMsyNjvlJ37HVMenpPWEGS1mw"; // Лучше использовать переменные окружения для токенов
            TelegramClient telegramClient = new OkHttpTelegramClient(botToken);
            PlayerDataService playerDataService = new PlayerDataService(new JsonParser());

            TelegramBot bot = new TelegramBot(telegramClient, playerDataService);
            TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
            botsApplication.registerBot(botToken, bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static class Main {
        public static void main(String[] args) {
            tgAPI(args);
        }
    }
}
