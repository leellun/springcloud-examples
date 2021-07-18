package com.newland.seataaccountservice.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.newland.seataaccountservice.dao"})
public class MyBatisConfig {

}
