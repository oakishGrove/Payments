package com.payments.notifications;

public interface NotificationsFacade {

    void initService();
    void sendNotification(String url, String body);
    void shutdown();
}
