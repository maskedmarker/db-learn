package org.example.learn.db.export;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class DbExportSchemaSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(DbExportSchemaSpringBootApplication.class);
    }
}
