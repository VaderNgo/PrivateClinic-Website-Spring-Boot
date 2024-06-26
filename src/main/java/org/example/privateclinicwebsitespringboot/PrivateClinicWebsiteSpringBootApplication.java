package org.example.privateclinicwebsitespringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication

public class PrivateClinicWebsiteSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrivateClinicWebsiteSpringBootApplication.class, args);
    }

}
