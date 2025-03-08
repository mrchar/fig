package com.example.former;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@EnableJpaAuditing
@SpringBootApplication
@EnableSpringDataWebSupport
public class FormerApplication {

  public static void main(String[] args) {
    SpringApplication.run(FormerApplication.class, args);
  }
}
