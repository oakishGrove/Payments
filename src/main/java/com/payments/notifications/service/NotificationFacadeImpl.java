package com.payments.notifications.service;

import com.payments.notifications.NotificationsFacade;
import com.payments.notifications.repo.NotificationAttemptDao;
import com.payments.notifications.repo.NotificationAttemptEntity;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class NotificationFacadeImpl implements NotificationsFacade {

    private static Logger logger = LoggerFactory.getLogger(NotificationFacadeImpl.class);

    private RestTemplate restTemplate;
    private NotificationAttemptDao notificationAttemptDao;
    private ExecutorService executorService;

    public NotificationFacadeImpl(RestTemplate restTemplate, NotificationAttemptDao notificationAttemptDao) {
        this.restTemplate = restTemplate;
        this.notificationAttemptDao = notificationAttemptDao;
    }

    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    @PostConstruct
    public void initService() {
        executorService = new ThreadPoolExecutor(5, 5, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    }

    @Override
    public void sendNotification(String url, String details) {

        if (executorService == null || executorService.isShutdown() || executorService.isTerminated()) {
            // not proper solution, but in this case it will do
            return;
        }

        AcceptsCounter onError = (int counter) -> {

            concludeProcessing(url, details, false, counter);
        };

        AcceptsCounter onSuccess = (int counter) -> {

            concludeProcessing(url, details, true, counter);
        };

        var runner = new CustomRetryWrapper(() -> makeNotification(url, details), onSuccess, onError, 3, 500, 2);

        try {
            executorService.submit(runner);
        } catch (RejectedExecutionException ex) {
            logger.warn("notification service thread pool rejected runner: " + ex.getMessage(), ex);
            onError.run(0);
        }

    }


    @Transactional
    public void concludeProcessing(String url, String details, boolean success, int counter) {

        logger.debug("saving notification submission result");

        var dbEntity = new NotificationAttemptEntity();
        dbEntity.setAttemptCounter(counter);
        dbEntity.setEndOfProcessing(LocalDateTime.now());
        dbEntity.setTargetUrl(url);
        dbEntity.setType(details);
        dbEntity.setSuccess(success);

        notificationAttemptDao.save(dbEntity);
    }

    public void makeNotification(String url, String body) {
        var response = restTemplate.getForEntity(url, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Response status is not 2xx");
        }
    }

    @Override
    @PreDestroy
    public void shutdown() {
        try {
            executorService.awaitTermination(4, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("error during shutdown", e);
        }
        executorService.shutdown();
    }
}
