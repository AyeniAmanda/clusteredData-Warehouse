package com.example.clustereddatawarehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class ClusteredDataWarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClusteredDataWarehouseApplication.class, args);
    }

}
