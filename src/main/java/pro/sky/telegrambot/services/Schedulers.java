package pro.sky.telegrambot.services;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.util.Collection;

@Component
public class Schedulers {
    private Logger logger = LoggerFactory.getLogger(Schedulers.class);

    @Autowired
    private TelegramBot telegramBot;

    private volatile Collection<NotificationTask> notificationTasks;
    private final NotificationTaskRepository notificationTaskRepository;
    private LocalDateTime lastDateTimeRun;

    public Schedulers(NotificationTaskRepository notificationTaskRepository) {
        this.notificationTaskRepository = notificationTaskRepository;
    }

    /*
    * выполняется каждую минуту и отправляет все неотпарвленные сообщения с момента последнего запуска
    * чтоб избежать дублирования отправки при наложении запусков
    * если послений запуска не заполнен значит счиает что приложение было аварйно остановлено
    *  и перепраоверим все сообщения и отправим что не были отправлены
    *
    * */
    @Scheduled(fixedRate  = 60 * 1_000L)
    @Async
    public void runSendMessages() {
        logger.info("runSendMessages. Started");
        notificationTasks = null;
        LocalDateTime localStartDateTimeScheduler;
        LocalDateTime lastStart = getLastDateTimeRun();
        localStartDateTimeScheduler = setNewLastDateTimeRun();
        if (lastStart == null) {
            notificationTasks =   notificationTaskRepository.findAllNotSendTasks(localStartDateTimeScheduler);
        } else {
            notificationTasks =   notificationTaskRepository.findNotSendTasksFromLastStart(lastStart, localStartDateTimeScheduler);
        }
        if (notificationTasks != null) {
            notificationTasks.stream().forEach((n)-> processTask(n));
        }
        logger.info("runSendMessages. Finished");
    }

    private void processTask(NotificationTask notificationTask){
        logger.info("processTask: {}", notificationTask);
        SendResponse sendResponse = sendResponse(Long.parseLong(notificationTask.getChatId()), notificationTask.getResponseText());
        if (sendResponse.isOk()) {
            notificationTask.setSend(true);
            notificationTask.setDateSend(LocalDateTime.now());
            notificationTaskRepository.save(notificationTask);
        }
    }


    private SendResponse sendResponse(long chatId, String responseText){
        SendMessage sendMessage = new SendMessage(chatId, responseText);
        SendResponse response = telegramBot.execute(sendMessage);
        logger.info("Response send: {} ", response);
        return response;
    }

    private synchronized LocalDateTime getLastDateTimeRun(){
        return this.lastDateTimeRun;
    }

    private synchronized LocalDateTime setNewLastDateTimeRun(){
        this.lastDateTimeRun = LocalDateTime.now();
        return this.lastDateTimeRun;
    }



}
