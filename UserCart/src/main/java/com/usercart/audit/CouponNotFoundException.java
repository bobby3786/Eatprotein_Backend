package com.usercart.audit;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CouponNotFoundException extends RuntimeException {
    public CouponNotFoundException(String couponCode) {
        super("Coupon with code '" + couponCode + "' not found or is invalid.");
    }
}
