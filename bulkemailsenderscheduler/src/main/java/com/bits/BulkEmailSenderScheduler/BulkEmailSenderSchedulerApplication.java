package com.bits.BulkEmailSenderScheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class BulkEmailSenderSchedulerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BulkEmailSenderSchedulerApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BulkEmailSenderSchedulerApplication.class);
    }

}
