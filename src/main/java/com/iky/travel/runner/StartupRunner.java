package com.iky.travel.runner;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StartupRunner {

  @Value("${greeting}")
  private String greeting;

  @Bean
  ApplicationRunner appStarted() {
    return args -> {
      log.info("App started and running: {}", greeting);
    };
  }
}
