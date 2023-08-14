package com.example.couponapi.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AppliedUserRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public AppliedUserRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Long addUserId(Long userId) {
        return redisTemplate.opsForSet()
                .add("applied_user", userId.toString());
    }

    public Long addUserIdCoupon(Long userId) {
        return redisTemplate.opsForValue()
                .increment("coupon:" + userId);

    }
}
