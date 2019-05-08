package com.vanguard8;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@MapperScan(basePackages = "com.vanguard8.*.dao")
public class Vanguard8FrameworkApplication extends SpringBootServletInitializer {

//    @Bean
//    public HttpMessageConverters fastJsonHttpMessageConverters() {
//        //1.需要定义一个convert转换消息的对象;  
//        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
//        //2处理ie浏览器保存数据时出现下载json数据问题  
//        List<MediaType> fastMediaTypes = new ArrayList<>();
//        fastMediaTypes.add(MediaType.TEXT_PLAIN);
//        //3.在convert中添加配置信息.  
//        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
//        HttpMessageConverter<?> converter = fastJsonHttpMessageConverter;
//        return new HttpMessageConverters(converter);
//    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Vanguard8FrameworkApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Vanguard8FrameworkApplication.class, args);
    }
}
