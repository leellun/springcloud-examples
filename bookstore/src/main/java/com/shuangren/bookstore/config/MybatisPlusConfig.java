package com.shuangren.bookstore.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.shuangren.bookstore")
public class MybatisPlusConfig {

}
