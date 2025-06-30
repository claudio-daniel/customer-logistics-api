package com.nauta.api.logistics.customer.config

import com.nauta.api.logistics.customer.exceptionhandling.CustomAccessDeniedHandler
import com.nauta.api.logistics.customer.exceptionhandling.CustomBasicAuthenticationEntryPoint
import com.nauta.api.logistics.customer.filter.AuthoritiesLoggingAfterFilter
import com.nauta.api.logistics.customer.filter.AuthoritiesLoggingAtFilter
import com.nauta.api.logistics.customer.filter.CsrfCookieFilter
import com.nauta.api.logistics.customer.filter.JWTTokenGeneratorFilter
import com.nauta.api.logistics.customer.filter.JWTTokenValidatorFilter
import com.nauta.api.logistics.customer.filter.RequestValidationBeforeFilter
import com.nauta.api.logistics.customer.model.constants.SecurityConstants
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource

fun corsConfigurationSource(): CorsConfigurationSource {
    return CorsConfiguration().apply {
        allowedOrigins = listOf(SecurityConstants.ALLOWED_ORIGIN)
        allowedMethods = listOf(SecurityConstants.ALLOWED_METHODS)
        allowCredentials = true
        allowedHeaders = listOf(SecurityConstants.ALLOWED_HEADERS)
        exposedHeaders = listOf(SecurityConstants.AUTHORIZATION_HEADER)
        maxAge = SecurityConstants.CORS_MAX_AGE
    }.let { config ->
        CorsConfigurationSource { config }
    }
}

fun HttpSecurity.configureCsrf(): HttpSecurity {
    csrf {
        it
            .csrfTokenRequestHandler(CsrfTokenRequestAttributeHandler())
            .ignoringRequestMatchers(SecurityConstants.REGISTER_ENDPOINT, SecurityConstants.LOGIN_ENDPOINT)
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
    }
    return this
}

fun HttpSecurity.registerCustomFilters(): HttpSecurity {
    addFilterAfter(CsrfCookieFilter(), BasicAuthenticationFilter::class.java)
    addFilterBefore(RequestValidationBeforeFilter(), BasicAuthenticationFilter::class.java)
    addFilterAfter(AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter::class.java)
    addFilterAt(AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter::class.java)
    addFilterAfter(JWTTokenGeneratorFilter(), BasicAuthenticationFilter::class.java)
    addFilterBefore(JWTTokenValidatorFilter(), BasicAuthenticationFilter::class.java)
    return this
}

fun HttpSecurity.configureAuthorization(): HttpSecurity {
    authorizeHttpRequests { http ->
        http
            .requestMatchers(SecurityConstants.ORDERS_ENDPOINT).hasAnyRole(SecurityConstants.USER_ROLE, SecurityConstants.ADMIN_ROLE)
            .requestMatchers(SecurityConstants.CONTAINERS_ENDPOINT).hasRole(SecurityConstants.USER_ROLE)
            .requestMatchers(SecurityConstants.USER_ENDPOINT).authenticated()
            .requestMatchers(
                SecurityConstants.ERROR_ENDPOINT,
                SecurityConstants.REGISTER_ENDPOINT,
                SecurityConstants.INVALID_SESSION_ENDPOINT,
                SecurityConstants.LOGIN_ENDPOINT
            )
            .permitAll()
    }
    return this
}

fun HttpSecurity.configureHttpBasic(): HttpSecurity {
    httpBasic { it.authenticationEntryPoint(CustomBasicAuthenticationEntryPoint()) }
    return this
}

fun HttpSecurity.configureExceptionHandling(): HttpSecurity {
    exceptionHandling { it.accessDeniedHandler(CustomAccessDeniedHandler()) }
    return this
}