package org.restler.todo;

import com.fasterxml.jackson.module.paranamer.ParanamerModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@EnableAutoConfiguration
@Configuration
@EnableWebMvc
public class Starter extends WebMvcConfigurerAdapter {

    @Override public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        ParanamerModule module = new ParanamerModule();
        converters.stream().
                filter(c -> c instanceof MappingJackson2HttpMessageConverter).
                forEach(c -> ((MappingJackson2HttpMessageConverter) c).getObjectMapper().registerModule(module));
    }


    @Bean Todos todos() {
        return new TodosController();
    }

    public static void main(String[] args) {
        SpringApplication.run(Starter.class, args);
    }

}
