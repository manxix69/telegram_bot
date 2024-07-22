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

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    private final String HELLO_TEXT = "Hy my friend! It`s bot for tests!";
    private final String STATEMENT_START = "/start";

    private final Pattern PATTERN_STATEMENT_START = Pattern.compile(STATEMENT_START);

    private Matcher matcher;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {

        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            // Process your updates here
            matcher = PATTERN_STATEMENT_START.matcher(update.message().text());
            if (matcher.matches()) {
                SendMessage message = new SendMessage(update.message().chat().id(), HELLO_TEXT);
                SendResponse response = telegramBot.execute(message);
                logger.info("Processing response: {}", response);
            }
//            update.message().text();    // Текст сообщения
//            update.message().chat().id(); //идентификатор чата, в который нужно отправить сообщение

//            SendMessage message = new SendMessage(update.message().chat().id(), messageText);
//            SendResponse response = telegramBot.execute(message);
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
