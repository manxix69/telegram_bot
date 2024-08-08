package pro.sky.telegrambot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "z_notification_task")
public class NotificationTask {
    @Id
    @GeneratedValue
    private Long id;
    private String chatId;
    private String responseText;
    private LocalDateTime dateCreate;
    private LocalDateTime dateStartSend;
    private Boolean isSend;
    private LocalDateTime dateSend;

    private NotificationTask() {
    }

    public NotificationTask(String chatId, LocalDateTime dateStartSend, String responseText) {
        this.chatId = chatId;
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
