package com.vanguard8;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan(basePackages = "com.vanguard8.*.dao")
public class Vanguard8FrameworkApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Vanguard8FrameworkApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Vanguard8FrameworkApplication.class, args);
    }
}
