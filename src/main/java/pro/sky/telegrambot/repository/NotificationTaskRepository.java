package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pro.sky.telegrambot.model.NotificationTask;

import java.time.LocalDateTime;
import java.util.Collection;

public interface NotificationTaskRepository extends JpaRepository<NotificationTask, Long> {

    @Query(value =
            "select n.* " +
            "  from z_notification_task n" +
            " where n.is_send = false" +
            "   and n.date_start_send <= :dateTime"
            , nativeQuery = true )
    Collection<NotificationTask> findAllNotSendTasks(@Param("dateTime") LocalDateTime dateTime);

    @Query(value =
            "select n.* " +
                    "  from z_notification_task n" +
                    " where n.is_send = false" +
                    "   and n.date_start_send <= :dateTime" +
                    "   and n.date_start_send >  :lastStart"
            , nativeQuery = true )
    Collection<NotificationTask> findNotSendTasksFromLastStart(@Param("lastStart") LocalDateTime lastStart
                                                            ,  @Param("dateTime") LocalDateTime dateTime);

}
