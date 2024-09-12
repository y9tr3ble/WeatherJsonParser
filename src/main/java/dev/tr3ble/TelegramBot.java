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
            String messageText = update.getMessage().getText().trim();

            if (messageText.startsWith("/setlocation")) {
                String[] parts = messageText.split(" ", 2);
                if (parts.length == 2) {
                    setLocationAndRespond(chatId, parts[1]);
                } else {
                    sendMessage(chatId, "Пожалуйста, укажите локацию. Например: /setlocation Москва");
                }
            } else if (!messageText.startsWith("/")) {
                setLocationAndRespond(chatId, messageText);
            } else {
                sendWeatherInfo(chatId);
            }
        }
    }

    private void setLocationAndRespond(long chatId, String newLocation) {
        weatherService.setLocation(newLocation);
        sendMessage(chatId, "Локация изменена на: " + newLocation);
        sendWeatherInfo(chatId);
    }

    private void sendWeatherInfo(long chatId) {
        try {
            String weatherInfo = weatherService.getWeather();
            String responseText = (weatherInfo != null)
                    ? "Город: " + weatherService.getCurrentLocation() + "\n" + weatherInfo
                    : "Ошибка получения данных о погоде.";
            sendMessage(chatId, responseText);
        } catch (IOException e) {
            e.printStackTrace();
            sendMessage(chatId, "Произошла ошибка при обработке вашего запроса.");
        }
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
