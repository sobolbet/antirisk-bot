package com.worldbet.antirisk_bot.configs;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class ExecutorConfig {

    @Bean (destroyMethod = "shutdown")
    public ExecutorService executorService () {

        return new ThreadPoolExecutor(6, 8, 60L, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(100),
                new ThreadFactory() {
                    private final AtomicInteger counter = new AtomicInteger(1);

                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r);
                        t.setName("event-executor-" + counter.getAndIncrement());
                        t.setDaemon(false);
                        return t;
                    }
                },
                new ThreadPoolExecutor.CallerRunsPolicy());

    }

}
