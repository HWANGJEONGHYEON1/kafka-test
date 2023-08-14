package com.example.couponconsumer.repository;


import com.example.couponconsumer.domain.FailCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FailCouponRepository extends JpaRepository<FailCoupon, Long> {
}
