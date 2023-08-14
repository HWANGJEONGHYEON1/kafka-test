package com.example.couponconsumer.consumer;

import com.example.couponconsumer.domain.Coupon;
import com.example.couponconsumer.domain.FailCoupon;
import com.example.couponconsumer.repository.CouponRepository;
import com.example.couponconsumer.repository.FailCouponRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CouponCreatedConsumer {
    private final CouponRepository couponRepository;
    private final FailCouponRepository failCouponRepository;
    private static final Logger logger = LoggerFactory.getLogger(CouponCreatedConsumer.class);

    public CouponCreatedConsumer(CouponRepository couponRepository, FailCouponRepository failCouponRepository) {
        this.couponRepository = couponRepository;
        this.failCouponRepository = failCouponRepository;
    }

    @KafkaListener(topics = "coupon_create", groupId = "coupon-group-1")
    public void listener(Long userId) {

        try {
            couponRepository.save(new Coupon(userId));
        } catch (Exception e) {
            logger.error("쿠폰 발급 실패 user : {}", userId);
            failCouponRepository.save(new FailCoupon(userId));
        }
    }
}
