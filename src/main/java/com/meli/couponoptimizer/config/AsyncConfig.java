package com.meli.couponoptimizer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

  @Value("${AsyncConfig.core-pool-size}")
  private int corePoolSize;

  @Value("${AsyncConfig.max-pool-size}")
  private int maxPoolSize;

  @Value("${AsyncConfig.queue-capacity}")
  private int queueCapacity;

  @Value("${AsyncConfig.thread-name-prefix}")
  private String threadNamePrefix;

  @Bean(name = "taskExecutor")
  public Executor taskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(corePoolSize);
    executor.setMaxPoolSize(maxPoolSize);
    executor.setQueueCapacity(queueCapacity);
    executor.setThreadNamePrefix(threadNamePrefix);
    executor.initialize();
    return executor;
  }
}
