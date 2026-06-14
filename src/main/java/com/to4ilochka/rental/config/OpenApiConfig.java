package com.to4ilochka.rental.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Сервіс управління системою прокату автомобілів")
                        .version("1.0.0")
                        .description("REST API для управління клієнтами, автомобілями, бронюваннями та пунктами прокату. Система побудована з використанням Spring Boot 4, Spring Data JPA та PostgreSQL.")
                        .contact(new Contact()
                                .name("Кафедра Інженерії Програмного Забезпечення")
                                .email("support@university.edu.ua")));
    }
}
