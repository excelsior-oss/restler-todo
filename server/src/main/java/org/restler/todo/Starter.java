package org.restler.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@Configuration
@EnableWebMvc
public class Starter {

    @Bean Todos todos() {
        return new TodosController();
    }

    public static void main(String[] args) {
        SpringApplication.run(Starter.class, args);
    }

}
