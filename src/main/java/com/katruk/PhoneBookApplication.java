package com.katruk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScan({"{$dao}", "com.katruk.config", "com.katruk.domain", "com.katruk.controller"})
public class PhoneBookApplication {
  public static void main(String[] args) {
    SpringApplication.run(PhoneBookApplication.class, args);
  }
}
