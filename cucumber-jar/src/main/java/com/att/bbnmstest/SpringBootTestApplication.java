package com.att.bbnmstest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan(basePackages = { "com.att" })
@PropertySource("classpath:application.properties")
public class SpringBootTestApplication
{

   public static void main(String[] args)
   {
      SpringApplication.run(SpringBootTestApplication.class, args);
   }
}
