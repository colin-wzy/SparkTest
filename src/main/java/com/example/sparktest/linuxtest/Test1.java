package com.example.sparktest.linuxtest;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;

import java.util.Arrays;
import java.util.List;

public class Test1 {
    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder().appName("SparkTest").master("local").getOrCreate();

        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        JavaRDD<Integer> rdd = spark.createDataset(data, Encoders.INT()).javaRDD();

        // 示例 1: 过滤出偶数
        JavaRDD<Integer> evenNumbersRDD = rdd.filter(number -> number % 2 == 0);
        System.out.println("Even numbers: " + evenNumbersRDD.collect());

        // 示例 2: 对所有元素进行求和
        int sum = rdd.reduce(Integer::sum);
        System.out.println("Sum of all numbers: " + sum);

        // 示例 3: 将所有元素映射为平方
        JavaRDD<Integer> squaredRDD = rdd.map(number -> number * number);
        System.out.println("Squared numbers: " + squaredRDD.collect());

        spark.stop();
    }
}
