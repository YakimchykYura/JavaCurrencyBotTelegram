package org.example;
import lombok.SneakyThrows;
import org.example.command.NotificationSetting;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Notification implements Runnable {
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    TelegramCurrencyBot bot;
    Map<Long, NotificationSetting.Notification> settings;

    public Notification(Map<Long, NotificationSetting.Notification> settings, TelegramCurrencyBot bot) {
        System.out.println("Start notification thread...");
        this.settings = settings;
        this.bot = bot;
    }

    @Override
    @SneakyThrows
    public void run() {
        while (true) {
            long delay = ChronoUnit.MILLIS.between(
                    LocalTime.now(),LocalTime.now().getHour() == 23 ? LocalTime.MAX :
                            LocalTime.of(LocalTime.now().getHour() + 1, 0, 0));

            scheduler.schedule(() -> {
                settings.entrySet().stream()
                        .filter(x -> x.getValue().getTime() == LocalTime.now().getHour())
                        .forEach(x -> {
                            System.out.println("Message sent ID: " + x.getKey());
                            try {
                                bot.sendNotification(x.getKey());
                            } catch (TelegramApiException e) {
                                throw new RuntimeException(e);
                            }
                        });
            }, delay, TimeUnit.MILLISECONDS);

            Thread.sleep(1000 * 60 * 60);

        }
    }
}
