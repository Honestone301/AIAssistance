package com.aiassistance;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.aiassistance.Mapper")
@EnableAspectJAutoProxy // 启用AOP
public class AiAssistanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiAssistanceApplication.class, args);
    }

}
