package com.nauta.api.logistics.customer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@SpringBootApplication
@EnableWebSecurity
class CustomerLogisticsApiApplication

fun main(args: Array<String>) {
	runApplication<CustomerLogisticsApiApplication>(*args)
}
