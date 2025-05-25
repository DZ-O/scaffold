package com.beikdz.scaffold;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.beikdz.scaffold.repository")
public class ScaffoldApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScaffoldApplication.class, args);
    }
}
