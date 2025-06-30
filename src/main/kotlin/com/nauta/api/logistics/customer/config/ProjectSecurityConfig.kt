package com.nauta.api.logistics.customer.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.SecurityFilterChain

@Configuration
class ProjectSecurityConfig {

    @Bean
    @Throws(exceptionClasses = [Exception::class])
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http.sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .cors { cors -> cors.configurationSource(corsConfigurationSource()) }
            .configureCsrf()
            .registerCustomFilters()
            .configureAuthorization()
            .configureHttpBasic()
            .configureExceptionHandling()
            .formLogin(Customizer.withDefaults())
            .build()
    }

    @Bean
    fun authenticationManager(
        userDetailsService: UserDetailsService,
    ): AuthenticationManager {
        val authenticationProvider = UsernamePwdAuthenticationProvider(userDetailsService)
        val providerManager = ProviderManager(authenticationProvider)

        providerManager.isEraseCredentialsAfterAuthentication = false

        return providerManager
    }
}