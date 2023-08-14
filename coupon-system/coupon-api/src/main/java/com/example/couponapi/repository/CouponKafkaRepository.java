package com.example.couponapi.repository;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CouponKafkaRepository {

    private final KafkaTemplate<String, Long> kafkaTemplate;

    public CouponKafkaRepository(KafkaTemplate<String, Long> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void createCoupon(Long userId) {
        kafkaTemplate.send("coupon_create", userId);
    }
}
