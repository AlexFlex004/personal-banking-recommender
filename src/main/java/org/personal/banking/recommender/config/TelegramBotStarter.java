package org.personal.banking.recommender.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.personal.banking.recommender.bot.TelegramRecommendationBot;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBotStarter {

    private final TelegramRecommendationBot bot;
    private final TelegramBotsApi telegramBotsApi;

    @PostConstruct
    public void start() {
        try {
            telegramBotsApi.registerBot(bot);
        } catch (Exception e) {
            // не валим всё приложение
            log.error("Telegram bot failed to start", e);
        }
    }
}
