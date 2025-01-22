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
                .config("spark.driver.host", "127.0.0.1")
                .config("spark.hadoop.ipc.client.connect.timeout", "3000") // 设置连接超时时间（单位：毫秒）
                .config("spark.hadoop.ipc.client.connect.max.retries.on.timeouts", "3") // 设置连接超时最大重试次数
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
