package com.example.couponconsumer.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class FailCoupon {

    @Id @GeneratedValue
    private Long id;

    private Long userId;

    public FailCoupon() {
    }

    public FailCoupon(Long userId) {
        this.userId = userId;
    }
}
