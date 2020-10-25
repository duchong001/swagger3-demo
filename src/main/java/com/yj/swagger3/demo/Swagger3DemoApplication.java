package com.yj.swagger3.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@SpringBootApplication
public class Swagger3DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(Swagger3DemoApplication.class, args);
    }

}
