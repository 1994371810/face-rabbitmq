package com.gjw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gjw.mapper")
public class Mq01Application {

    public static void main(String[] args) {
        SpringApplication.run(Mq01Application.class, args);
    }

}
