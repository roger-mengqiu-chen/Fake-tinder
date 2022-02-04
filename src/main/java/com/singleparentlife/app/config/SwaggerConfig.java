package com.singleparentlife.app.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return (new Docket(DocumentationType.SWAGGER_2))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false);
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("SingleParentLife APIs", "APIs of SingleParentLife application",
                "1.0",
                "https://www.singleparentlife.app/",
                new Contact("SingleParentLife.app", "https://www.singleparentlife.app/", "info@singleparentlife.app"),
                "License of API",
                "https://www.singleparentlife.app/",
                Collections.emptyList());
    }
}
