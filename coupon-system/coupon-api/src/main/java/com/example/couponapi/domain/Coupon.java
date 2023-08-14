package com.example.couponapi.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Coupon {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private Long userId;

    public Coupon() {
    }

    public Coupon(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
