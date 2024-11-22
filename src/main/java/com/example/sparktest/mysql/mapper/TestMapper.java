package com.example.sparktest.mysql.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TestMapper {

    void batchInsert(@Param("valueList") List<Map<String, String>> valueList);
}
