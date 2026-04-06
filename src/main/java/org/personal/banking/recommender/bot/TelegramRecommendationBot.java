package org.personal.banking.recommender.bot;

import org.personal.banking.recommender.dto.RecommendationDto;
import org.personal.banking.recommender.service.RecommendationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class TelegramRecommendationBot extends TelegramLongPollingBot {

    private final RecommendationService recommendationService;

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Value("${telegram.bot.token}")
    private String botToken;

    public TelegramRecommendationBot(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        String messageText = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();

        System.out.println("Received message: " + messageText);

        //start
        if (messageText.equals("/start")) {
            sendMessage(chatId,
                    "Здравствуйте!\n\n" +
                            "Я бот рекомендаций банка.\n" +
                            "Для получения персональных рекомендаций" +
                            "Используй команду:\n\n" +
                            "/recommend @username");
            return;
        }

        //recommend
        if (!messageText.startsWith("/recommend")) {

            sendMessage(chatId,
                    "Неизвестная команда.\n\n" +
                            "Используйте:\n" +
                            "/recommend @username");

            return;
        }

        String[] parts = messageText.split(" ");

        if (parts.length != 2) {
            sendMessage(chatId, "Введите команду в формате:\n/recommend @username");
            return;
        }

        String username = parts[1];

        // убираем @ если есть
        if (username.startsWith("@")) {
            username = username.substring(1);
        }

        try {

            List<RecommendationDto> recommendations =
                    recommendationService.getRecommendationsByUsername(username);

            String fullName =
                    recommendationService.getFullNameByUsername(username);

            if (recommendations.isEmpty()) {
                sendMessage(chatId, "Нет рекомендаций для пользователя " + fullName);
                return;
            }

            StringBuilder response = new StringBuilder();

            response.append("Здравствуйте, ")
                    .append(fullName)
                    .append("!\n\n")
                    .append("Рекомендации для вас:\n");

            for (RecommendationDto recommendation : recommendations) {
                response.append("• ")
                        .append(recommendation.getName())
                        .append("\n");
            }

            sendMessage(chatId, response.toString());

        } catch (Exception e) {

            e.printStackTrace();

            sendMessage(chatId, "Пользователь не найден");
        }
    }

    private void sendMessage(Long chatId, String text) {

        SendMessage message = new SendMessage();

        message.setChatId(chatId.toString());
        message.setText(text);

        try {
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
