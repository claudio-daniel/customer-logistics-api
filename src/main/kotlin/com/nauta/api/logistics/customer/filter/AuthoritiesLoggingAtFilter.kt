package com.nauta.api.logistics.customer.filter

import com.nauta.api.logistics.customer.util.logger
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import java.io.IOException

class AuthoritiesLoggingAtFilter : Filter {
    private val log = logger()

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        log.info("Authentication Validation is in progress")

        chain.doFilter(request, response)
    }
}