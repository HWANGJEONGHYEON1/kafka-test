package com.example.couponapi.service;

import com.example.couponapi.repository.CouponRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApplyServiceTest {

    @Autowired
    ApplyService applyService;

    @Autowired
    CouponRepository couponRepository;

    @Test
    @DisplayName("한번만 응모")
    void apply() {
        applyService.apply(1L);
        assertThat(couponRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("여러명 응모")
    void apply_multiply() throws InterruptedException {
        int threadCount = 1000;
        ExecutorService executors = Executors.newFixedThreadPool(32);
        // 모든 요청이 끝날때까지 기다림, 다른 스레드에서 끝날때까지 기다려줌
        CountDownLatch count = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            long userId = i;
            executors.submit(() -> {
                try {
                    applyService.apply(userId);
                } finally {
                    count.countDown();
                }
            });
        }
        count.await();

        Thread.sleep(2000l);
        assertThat(couponRepository.count()).isEqualTo(100);
    }

    @Test
    @DisplayName("한명당 하나의 쿠폰만 가능")
    void apply_multiply_one_user_coupon_limit() throws InterruptedException {
        int threadCount = 1000;
        ExecutorService executors = Executors.newFixedThreadPool(32);
        // 모든 요청이 끝날때까지 기다림, 다른 스레드에서 끝날때까지 기다려줌
        CountDownLatch count = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            executors.submit(() -> {
                try {
                    applyService.applyUserLimitedOneCoupon(100L);
                } finally {
                    count.countDown();
                }
            });
        }
        count.await();

        assertThat(couponRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("한명당 세개의 쿠폰만 가능")
    void apply_multiply_one_user_coupon_limit_3() throws InterruptedException {
        int threadCount = 1000;
        ExecutorService executors = Executors.newFixedThreadPool(32);

        // 모든 요청이 끝날때까지 기다림, 다른 스레드에서 끝날때까지 기다려줌
        CountDownLatch count = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            executors.submit(() -> {
                try {
                    applyService.applyUserLimitedThreeCoupon(100L);
                } finally {
                    count.countDown();
                }
            });
        }
        count.await();
        Thread.sleep(1000l);
        assertThat(couponRepository.count()).isEqualTo(3);
    }
}