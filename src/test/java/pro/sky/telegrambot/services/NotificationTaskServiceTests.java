package pro.sky.telegrambot.services;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class NotificationTaskServiceTests {
    @Autowired
    private NotificationTaskService notificationTaskService;

    @MockBean
    private NotificationTaskRepository notificationTaskRepository;

    @Test
    public void matchesWithPattern(){


        Mockito.when((notificationTaskRepository.save(any(NotificationTask.class)))).thenReturn(null);
        String result = notificationTaskService.processMassage(0L, "11.08.2024 13:00 Сделать домашнюю работу.");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Записал. Напомню тебе: "+ "2024-08-11T13:00" , result) ;
    }

    @Test
    public void notMatchesWithPattern(){

        Mockito.when((notificationTaskRepository.save(any(NotificationTask.class)))).thenReturn(null);
        String result = notificationTaskService.processMassage(0L, "11.08.2024 13:00!(~СИМВОЛ ДОБАВИЛ ЛИШНИЙ~) Сделать домашнюю работу.");
        Assertions.assertNotNull(result);
        Assertions.assertEquals("К сожалению, не удалось распознать шаблон команды для напоминая. Попробуй еще раз." , result) ;
    }

}
