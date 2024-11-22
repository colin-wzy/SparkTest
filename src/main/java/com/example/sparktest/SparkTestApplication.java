package com.example.sparktest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.sparktest.mysql.mapper")
public class SparkTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SparkTestApplication.class, args);
    }

}
