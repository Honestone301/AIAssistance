package com.aiassistance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy // 启用AOP
public class AiAssistanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiAssistanceApplication.class, args);

    }
}