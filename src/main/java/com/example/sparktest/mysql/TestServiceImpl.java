package com.example.sparktest.mysql;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.sparktest.mysql.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Administrator
 */
@Service
public class TestServiceImpl implements TestService {

    @Resource
    private TestMapper testMapper;

    @Override
    public void test1() {
        String string = "{\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"B\": \"Value1B\",\n" +
                "      \"A\": \"Value1A\",\n" +
                "      \"C\": \"Value1C\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"A\": \"Value2A\",\n" +
                "      \"B\": \"Value2B\",\n" +
                "      \"C\": \"Value2C\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"A\": \"Value3A\",\n" +
                "      \"C\": \"Value3C\",\n" +
                "      \"B\": \"Value3B\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        JSONObject entries = JSONUtil.parseObj(string);
        JSONArray data = entries.getJSONArray("data");
        List<Map<String, String>> result = new ArrayList<>();
        for (Object datum : data) {
            JSONObject jsonObject = (JSONObject) datum;
            Map<String, String> map = new HashMap<>();
            for (String key : jsonObject.keySet()) {
                map.put(key, jsonObject.getStr(key));
            }
            result.add(map);
        }
        testMapper.batchInsert(result);
    }
}
