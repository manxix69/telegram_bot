package pro.sky.telegrambot.services;

import com.pengrad.telegrambot.model.Message;

public interface NotificationTaskService {
    String processMassage(Long chatId, String textMassage);
}
