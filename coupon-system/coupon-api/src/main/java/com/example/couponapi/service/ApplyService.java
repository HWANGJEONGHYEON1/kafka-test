package com.example.couponapi.service;

import com.example.couponapi.repository.AppliedUserRepository;
import com.example.couponapi.repository.CouponCountRepository;
import com.example.couponapi.repository.CouponKafkaRepository;
import com.example.couponapi.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplyService {

    private final CouponRepository couponRepository;
    private final CouponCountRepository couponCountRepository;

    private final CouponKafkaRepository kafkaRepository;
    private final AppliedUserRepository appliedUserRepository;

    public ApplyService(CouponRepository couponRepository, CouponCountRepository couponCountRepository, CouponKafkaRepository kafkaRepository, AppliedUserRepository appliedUserRepository) {
        this.couponRepository = couponRepository;
        this.couponCountRepository = couponCountRepository;
        this.kafkaRepository = kafkaRepository;
        this.appliedUserRepository = appliedUserRepository;
    }

    public void apply(Long userId) {
        long count = couponCountRepository.increment();

        if (count > 100) {
            return;
        }

        kafkaRepository.createCoupon(userId);
    }

    public void applyUserLimitedOneCoupon(Long userId) {

        if (appliedUserRepository.addUserId(userId) != 1) {
            return;
        }
        long count = couponCountRepository.increment();

        if (count > 100) {
            return;
        }

        kafkaRepository.createCoupon(userId);
    }

    public void applyUserLimitedThreeCoupon(Long userId) {

        Long userCouponCount = appliedUserRepository.addUserIdCoupon(userId);

        if (userCouponCount > 3) {
            return;
        }
        long count = couponCountRepository.increment();

        if (count > 100) {
            return;
        }

        kafkaRepository.createCoupon(userId);
    }
}
