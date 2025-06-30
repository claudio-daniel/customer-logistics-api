package com.nauta.api.logistics.customer.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.password.CompromisedPasswordChecker
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker

@Configuration
class PasswordConfiguration {
    @Bean
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    fun compromisedPasswordChecker(): CompromisedPasswordChecker = HaveIBeenPwnedRestApiPasswordChecker()
}