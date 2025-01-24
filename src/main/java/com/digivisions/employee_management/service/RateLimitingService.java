package com.digivisions.employee_management.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RateLimitingService {

    private final Bucket bucket;

    public RateLimitingService() {
        this.bucket = Bucket4j.builder().addLimit(Bandwidth.classic(15, Refill.intervally(15, Duration.ofMinutes(1)))).build();
    }

    public boolean Consume() {
        return bucket.tryConsume(1);
    }
}
