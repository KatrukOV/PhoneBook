package com.katruk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class PhoneBookApplication {

  public static void main(String[] args) {
    SpringApplication.run(PhoneBookApplication.class, args);
  }

}
