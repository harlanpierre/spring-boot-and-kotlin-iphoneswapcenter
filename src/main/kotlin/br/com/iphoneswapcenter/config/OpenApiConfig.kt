package br.com.iphoneswapcenter.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenApi(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("RESTful API with Kotlin and Spring Boot")
                    .version("V1")
                    .description("Some description about your API")
                    .termsOfService("All rights reserved to iPhone Swap Center")
                    .license(
                        License().name("iPhone Swap Center License")
                            .url("https://github.com/harlanpierre/spring-boot-and-kotlin-iphoneswapcenter")
                    )
            )
    }
}