package com.Hospital.Hospital.Management.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Hospital managementAPI")
                .version("1.0")
                .description("API for Hospital management with autheentication.")
                .contact(new Contact()
                    .name(" for API Support")
                    .email("ankitsharma.as420@gmail.com")));
    }
}
