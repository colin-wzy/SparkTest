package com.example.sparktest.spark;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scala.Tuple2;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping(value = "/spark")
public class SparkController {

    @Resource
    private SparkSession sparkSession;

    @GetMapping("/helloSpark1")
    public String helloSpark1() {
        // 创建一个简单的 DataFrame
        Dataset<Row> data = sparkSession.read().json("src/main/resources/people.json");

        // 展示 DataFrame 的内容
        data.show();

        // 执行一些 SQL 操作
        data.createOrReplaceTempView("people");
        Dataset<Row> sqlResult = sparkSession.sql("SELECT name, age FROM people WHERE age > 20");

        // 返回结果给客户端
        StringBuilder result = new StringBuilder("People older than 20:\n");
        sqlResult.collectAsList().forEach(row -> result.append(row.toString()).append("\n"));

        return result.toString();
    }

    @GetMapping("/helloSpark2")
    public String helloSpark2() {
        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        JavaRDD<Integer> rdd = sparkSession.createDataset(data, Encoders.INT()).javaRDD();

        // 3. 执行RDD操作
        // 示例 1: 过滤出偶数
        JavaRDD<Integer> evenNumbersRDD = rdd.filter(number -> number % 2 == 0);
        System.out.println("Even numbers: " + evenNumbersRDD.collect());

        // 示例 2: 对所有元素进行求和
        int sum = rdd.reduce(Integer::sum);
        System.out.println("Sum of all numbers: " + sum);

        // 示例 3: 将所有元素映射为平方
        JavaRDD<Integer> squaredRDD = rdd.map(number -> number * number);
        System.out.println("Squared numbers: " + squaredRDD.collect());
        return null;
    }

    @GetMapping("/helloSpark3")
    public String helloSpark3() {
        // 读取hdfs上的文件
        Dataset<String> lines = sparkSession.read().textFile("hdfs://node1:8020/input/word.txt");
        // 转换为 JavaRDD
        JavaRDD<String> linesRDD = lines.javaRDD();
        // 进行单词统计
        JavaPairRDD<String, Integer> wordCounts = linesRDD
                .flatMap(line -> Arrays.asList(line.split(" ")).iterator())
                .mapToPair(word -> new Tuple2<>(word, 1))
                .reduceByKey(Integer::sum);

        wordCounts.collect().forEach(System.out::println);
        return null;
    }
}
