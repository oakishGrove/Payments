package com.payments.notifications.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomRetryWrapperUnitTest {

    @Test
    void wrapperExecutesRetryNCounterTimes() {

        record Record(int[] val) {};
        var actualCounter = new Record(new int[]{0});
        int expectedRetriesCount = 2;

        var onFailure = new AcceptsCounter() {
            @Override
            public void run(int counter) {
                actualCounter.val[0] = counter;
            }
        };

        Runnable throwingRunner = () -> {throw new RuntimeException();};
        var wrapperRunner = new CustomRetryWrapper(throwingRunner, (a) -> {}, onFailure, expectedRetriesCount, 100, 2);

        wrapperRunner.run();

        assertEquals(expectedRetriesCount, actualCounter.val[0]);
    }


    @Test
    void onSuccessCalledDuringFirstExecution() {

        record Record(int[] val) {};
        var actualCounter = new Record(new int[]{0});
        int expectedRetriesCount = 1;

        var onSuccess = new AcceptsCounter() {
            @Override
            public void run(int counter) {
                actualCounter.val[0] = counter;
            }
        };

        var wrapperRunner = new CustomRetryWrapper(() -> {}, onSuccess, (a) -> {}, expectedRetriesCount, 100, 2);

        wrapperRunner.run();

        assertEquals(expectedRetriesCount, actualCounter.val[0]);
    }


    @Test
    void onSuccessCalledAfterFirstExecutionExecution() {

        record Record(int[] val) {};
        var actualCounter = new Record(new int[]{0});
        int expectedRetriesCount = 4;

        var onSuccess = new AcceptsCounter() {
            @Override
            public void run(int counter) {
                actualCounter.val[0] = counter;
            }
        };

        var mainRunnable = mock(Runnable.class);

        doThrow(new RuntimeException(""))
                .doNothing()
                .when(mainRunnable)
                .run();

        var wrapperRunner = new CustomRetryWrapper(mainRunnable, onSuccess, (a) -> {}, expectedRetriesCount, 100, 2);

        wrapperRunner.run();

        assertEquals(2, actualCounter.val[0]);
    }
}