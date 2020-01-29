package com.epam.esm.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("com.epam.esm")
@MapperScan("com.epam.esm.mapper")
public class DataBaseConfig {

}