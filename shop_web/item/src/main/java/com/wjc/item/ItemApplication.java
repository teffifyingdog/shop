package com.wjc.item;

import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = "com.wjc")
@EnableEurekaClient
public class ItemApplication {



    public static void main(String[] args) {
        SpringApplication.run(ItemApplication.class, args);
    }

}
