package com.hspring;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class EmployeeDirectoryApplication {

    public static void main(String[] args) {
         SpringApplication.run(EmployeeDirectoryApplication.class, args);
    }

}
