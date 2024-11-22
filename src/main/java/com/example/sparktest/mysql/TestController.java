package com.example.sparktest.mysql;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author Administrator
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {
    @Resource
    private TestService testService;

    @PostMapping("/test1")
    public void test1() {
        testService.test1();
    }
}
