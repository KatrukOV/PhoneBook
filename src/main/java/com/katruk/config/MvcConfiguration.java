package com.katruk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

//@Configuration
//@ComponentScan(basePackages="com.katruk")
//@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter {

//  @Bean
//  public ViewResolver getViewResolver(){
//    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
////    resolver.setPrefix("/WEB-INF/views/");
//    resolver.setPrefix("/templates/");
//    resolver.setSuffix(".jsp");
//    return resolver;
//  }

  public void addViewControllers(ViewControllerRegistry registry) {
//    registry.addViewController("/login").setViewName("login");
//    registry.addViewController("/").setViewName("contact");
//    registry.addViewController("/registration").setViewName("registration");
    registry.addViewController("/contact").setViewName("contact");
  }

//  @Override
//  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
////    configurer.enable();
//  }

}


/*
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class Controller extends WebMvcConfigurerAdapter {

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/home").setViewName("home");
    registry.addViewController("/").setViewName("home");
    registry.addViewController("/hello").setViewName("hello");
    registry.addViewController("/login").setViewName("login");
  }
}
 */