package com.newland.springcloud.alibaba.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 */
@Configuration
@MapperScan({"com.newland.springcloud.alibaba.dao"})
public class MyBatisConfig {
}
