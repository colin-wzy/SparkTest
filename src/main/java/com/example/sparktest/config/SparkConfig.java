package com.example.sparktest.config;

import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

@Configuration
public class SparkConfig {

    private SparkSession sparkSession;

    @Bean
    public SparkSession sparkSession() {
        sparkSession = SparkSession.builder()
                .appName("SparkTest")
                .master("local[*]")
//                .master("spark://192.168.248.101:7077")
                .getOrCreate();
        return sparkSession;
    }

    @PreDestroy
    public void closeSparkSession() {
        if (sparkSession != null) {
            sparkSession.stop();
        }
    }
}
