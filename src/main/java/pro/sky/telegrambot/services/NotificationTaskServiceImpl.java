package pro.sky.telegrambot.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NotificationTaskServiceImpl implements NotificationTaskService {

    private final NotificationTaskRepository notificationTaskRepository;
    private final Logger logger = LoggerFactory.getLogger(NotificationTaskService.class);

    private final String  STATEMENT_START_FOR_SCHEDULED = "(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})(\\s+)(.+)";
    private final Pattern PATTERN_STATEMENT_FOR_SCHEDULED = Pattern.compile(STATEMENT_START_FOR_SCHEDULED);

    private Matcher matcher;

    public NotificationTaskServiceImpl(NotificationTaskRepository notificationTaskRepository) {
        this.notificationTaskRepository = notificationTaskRepository;
    }

    @Override
    public String processMassage(Long chatId, String textMassage) {
        logger.info("NotificationTaskServiceImpl.processMassage: {} {}",chatId, textMassage);
        final String response;
        matcher = PATTERN_STATEMENT_FOR_SCHEDULED.matcher(textMassage);
        if (matcher.matches()) {
            final LocalDateTime dateStartSend = LocalDateTime.parse(matcher.group(1), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            saveMessageForScheduled(chatId, dateStartSend, "Напоминаю: " + matcher.group(3));
            response = "Записал. Напомню тебе: " + dateStartSend;
        } else {
            response = "К сожалению, не удалось распознать шаблон команды для напоминая. Попробуй еще раз.";
        }
        logger.info("Processing response: response={}", response);
        return response;
    }

    private void saveMessageForScheduled(Long chatId, LocalDateTime dateStartSend , String textMassage){
        NotificationTask notificationTask = new NotificationTask(chatId, dateStartSend, textMassage);
        logger.info("saveMessageForScheduled: {}", notificationTask);
        notificationTaskRepository.save(notificationTask);
    }

}
