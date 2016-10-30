package com.katruk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Locale;

@SpringBootApplication
public class PhoneBookApplication {

  public static void main(String[] args) {
    Locale.setDefault(Locale.ENGLISH);
    SpringApplication.run(PhoneBookApplication.class, args);
  }

}
