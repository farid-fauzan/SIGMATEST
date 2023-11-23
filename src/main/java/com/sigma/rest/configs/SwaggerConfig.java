package com.sigma.rest.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false) // Prevent default response messages
                ;

    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "REST API Service",
                "REST API Service",
                "1.0.3 20221024",
                "Terms of Service",
                new Contact("Backend", "", ""),
                "License of API", "API License URL", Collections.emptyList()
        );
    }

    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Bearer", "header");
    }
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(new SecurityReference("Authorization", new AuthorizationScope[0])))
                .build();
    }
}