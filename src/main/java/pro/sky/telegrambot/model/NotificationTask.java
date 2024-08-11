package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "z_notification_task")
public class NotificationTask {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="z_notification_task_id_seq")
    @SequenceGenerator(name="z_notification_task_id_seq", sequenceName="z_notification_task_id_seq", allocationSize=1)
    @Column(name = "ID")
    private Long id;

    private String chatId;
    private String responseText;
    private LocalDateTime dateCreate;
    private LocalDateTime dateStartSend;
    private Boolean isSend;
    private LocalDateTime dateSend;

    private NotificationTask() {
    }

    public NotificationTask(Long chatId, LocalDateTime dateStartSend, String responseText) {
        this.chatId = Long.toString(chatId);
        this.dateStartSend = dateStartSend;
        this.responseText = responseText;
        this.dateCreate = LocalDateTime.now();
        this.isSend = false;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }

    public void setDateSend(LocalDateTime dateSend) {
        this.dateSend = dateSend;
    }

    public void setDateStartSend(LocalDateTime dateStartSend) {
        this.dateStartSend = dateStartSend;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSend(Boolean send) {
        isSend = send;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public String getChatId() {
        return chatId;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public LocalDateTime getDateSend() {
        return dateSend;
    }

    public LocalDateTime getDateStartSend() {
        return dateStartSend;
    }

    public Long getId() {
        return id;
    }

    public Boolean getSend() {
        return isSend;
    }

    public String getResponseText() {
        return responseText;
    }

    @Override
    public String toString() {
        return "NotificationTask{" +
                "chatId='" + chatId + '\'' +
                ", id=" + id +
                ", responseText='" + responseText + '\'' +
                ", dateCreate=" + dateCreate +
                ", dateStartSend=" + dateStartSend +
                ", isSend=" + isSend +
                ", dateSend=" + dateSend +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
