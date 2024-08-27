package dev.tr3ble;

import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;

public class TelegramBot implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient telegramClient;
    private final PlayerDataService playerDataService;

    public TelegramBot(TelegramClient telegramClient, PlayerDataService playerDataService) {
        this.telegramClient = telegramClient;
        this.playerDataService = playerDataService;
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            try {
                Integer numPlayers = playerDataService.getPlayersNum();
                String responseText = (numPlayers != null)
                        ? "Players: " + numPlayers
                        : "Ошибка получения данных о игроках.";

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
