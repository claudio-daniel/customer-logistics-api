package com.nauta.api.logistics.customer.config

import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.OpenAPI
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenAPI(): OpenAPI = OpenAPI().info(
        Info()
            .title("Customer Logistics API")
            .version("1.0")
            .description("Secured API for register customer logistics data")
    )

    @Bean
    fun publicApi(): GroupedOpenApi = GroupedOpenApi.builder()
        .group("customers")
        .pathsToMatch("/api/**")
        .build()
}