package com.katruk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import java.util.Locale;

@SpringBootApplication
//@ComponentScan
//@EnableJpaRepositories(basePackages = {"com.katruk.dao"})
public class PhoneBookApplication {

  public static void main(String[] args) {
//    Locale.setDefault(Locale.ENGLISH);
    SpringApplication.run(PhoneBookApplication.class, args);
  }

//  @Bean
//  public SessionLocaleResolver localeResolver() {
//    SessionLocaleResolver slr = new SessionLocaleResolver();
//    slr.setDefaultLocale(Locale.ENGLISH);
//    return slr;
//  }


//  @Bean
//  public LocaleChangeInterceptor localeChangeInterceptor() {
//    LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
//    lci.setParamName("lang");
//    return lci;
//  }

//  @Override
//  public void addInterceptors(InterceptorRegistry registry) {
//    registry.addInterceptor(localeChangeInterceptor());
//  }
}
