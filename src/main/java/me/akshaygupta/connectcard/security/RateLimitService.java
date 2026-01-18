package me.akshaygupta.connectcard.security;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitService {

    private static final int MAX_REQUESTS = 60;
    private static final long WINDOW_SECONDS = 60;

    private static class Counter {
        int count;
        Instant windowStart;
    }

    private final ConcurrentHashMap<String, Counter> ipCounters = new ConcurrentHashMap<>();

    public boolean isAllowed(String key) {
        Instant now = Instant.now();

        Counter counter = ipCounters.computeIfAbsent(key, k -> {
            Counter c = new Counter();
            c.count = 0;
            c.windowStart = now;
            return c;
        });

        synchronized (counter) {
            if (now.isAfter(counter.windowStart.plusSeconds(WINDOW_SECONDS))) {
                counter.count = 0;
                counter.windowStart = now;
            }

            counter.count++;
            return counter.count <= MAX_REQUESTS;
        }
    }
}
