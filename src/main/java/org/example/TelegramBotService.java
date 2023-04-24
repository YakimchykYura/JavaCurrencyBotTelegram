package org.example;

import org.example.command.NotificationSetting;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TelegramBotService {
    private TelegramCurrencyBot telegramCurrencyBot;

    public TelegramBotService(){
        telegramCurrencyBot = new TelegramCurrencyBot(new DefaultBotOptions());
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(telegramCurrencyBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        new Thread(new Notification(NotificationSetting.getNotificationMap(), telegramCurrencyBot)).start();
    }

}
