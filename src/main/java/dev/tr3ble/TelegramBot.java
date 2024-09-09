package dev.tr3ble;

import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;

public class TelegramBot implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient telegramClient;
    private final WeatherService weatherService;

    public TelegramBot(TelegramClient telegramClient, WeatherService weatherService) {
        this.telegramClient = telegramClient;
        this.weatherService = weatherService;
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            try {
                String weatherInfo = weatherService.getWeather();
                String responseText = (weatherInfo != null)
                        ? "Город: " + weatherInfo
                        : "Ошибка получения данных о погоде.";

                SendMessage message = SendMessage.builder()
                        .chatId(chatId)
                        .text(responseText)
                        .build();

                telegramClient.execute(message);
            } catch (TelegramApiException | IOException e) {
                e.printStackTrace();
                SendMessage errorMessage = SendMessage.builder()
                        .chatId(chatId)
                        .text("Произошла ошибка при обработке вашего запроса.")
                        .build();
                try {
                    telegramClient.execute(errorMessage);
                } catch (TelegramApiException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
