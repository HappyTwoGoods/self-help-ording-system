package com.yangnan.selfhelpordingsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.yangnan.selfhelpordingsystem.dao")
@SpringBootApplication
public class SelfHelpOrdingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SelfHelpOrdingSystemApplication.class, args);
    }

}

