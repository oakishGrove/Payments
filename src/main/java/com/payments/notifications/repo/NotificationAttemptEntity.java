package com.payments.notifications.repo;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "notifications")
public class NotificationAttemptEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, name = "end_of_processing")
    private LocalDateTime endOfProcessing;

    @Column(name = "target_url")
    private String targetUrl;

    @Column(name = "attempt_counter")
    private Integer attemptCounter;

    @Column(name = "details")
    private String type;

    @Column(name = "success")
    private Boolean success;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getEndOfProcessing() {
        return endOfProcessing;
    }

    public void setEndOfProcessing(LocalDateTime endOfProcessing) {
        this.endOfProcessing = endOfProcessing;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public Integer getAttemptCounter() {
        return attemptCounter;
    }

    public void setAttemptCounter(Integer attemptCounter) {
        this.attemptCounter = attemptCounter;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        NotificationAttemptEntity that = (NotificationAttemptEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(endOfProcessing, that.endOfProcessing) && Objects.equals(targetUrl, that.targetUrl) && Objects.equals(attemptCounter, that.attemptCounter) && Objects.equals(type, that.type) && Objects.equals(success, that.success);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(endOfProcessing);
        result = 31 * result + Objects.hashCode(targetUrl);
        result = 31 * result + Objects.hashCode(attemptCounter);
        result = 31 * result + Objects.hashCode(type);
        result = 31 * result + Objects.hashCode(success);
        return result;
    }

    @Override
    public String toString() {
        return "NotificationAttemptEntity{" +
                "id=" + id +
                ", endOfProcessing=" + endOfProcessing +
                ", targetUrl='" + targetUrl + '\'' +
                ", attemptCounter=" + attemptCounter +
                ", details='" + type + '\'' +
                ", success=" + success +
                '}';
    }
}
