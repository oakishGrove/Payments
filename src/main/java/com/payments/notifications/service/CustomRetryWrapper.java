package com.payments.notifications.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomRetryWrapper implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(CustomRetryWrapper.class);

    private Runnable runnable;
    private final AcceptsCounter onSuccess;
    private final AcceptsCounter onFailure;
    private final int retryCount;
    private final int waitingMili;
    private final int mult;
    private int counter = 0;

    public CustomRetryWrapper(Runnable runnable, AcceptsCounter onSuccess, AcceptsCounter onFailure, int retryCount,
                              int waitingMili, int mult) {
        this.runnable = runnable;
        this.onSuccess = onSuccess;
        this.onFailure = onFailure;
        this.retryCount = retryCount;
        this.waitingMili = waitingMili;
        this.mult = mult;
    }

    @Override
    public void run() {
        while (counter < retryCount) {
            try {
                runnable.run();
                onSuccess.run(counter + 1);
                return;
            } catch (Exception ex) {
                logger.warn(Thread.currentThread().getName() + ": " + ex.getMessage(), ex
                        .getCause());
                try {
                    Thread.sleep(waitingMili + ((long) counter * mult));
                } catch (InterruptedException e) {
                    logger.info("Thread interrupted while sleeping", e);
                }
            }
            counter++;
        }
        onFailure.run(counter);
    }
}
