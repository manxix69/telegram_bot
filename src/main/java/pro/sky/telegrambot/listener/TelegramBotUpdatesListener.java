package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.services.NotificationTaskService;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    @Autowired
    private TelegramBot telegramBot;
    private final NotificationTaskService notificationTaskService;
    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public TelegramBotUpdatesListener(NotificationTaskService notificationTaskService) {
        this.notificationTaskService = notificationTaskService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            // Process your updates here
            final String textResponse;
            textResponse = notificationTaskService.processMassage(update.message().chat().id(), update.message().text());
            sendResponse(update.message().chat().id(), textResponse);
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void sendResponse(long chatId, String responseText){
        SendMessage sendMessage = new SendMessage(chatId, responseText);
        SendResponse response = telegramBot.execute(sendMessage);
        logger.info("Response send: {} ", response);
    }
}
