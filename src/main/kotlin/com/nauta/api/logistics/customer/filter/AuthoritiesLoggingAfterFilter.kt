package com.nauta.api.logistics.customer.filter

import com.nauta.api.logistics.customer.util.logger
import jakarta.servlet.*
import org.springframework.security.core.context.SecurityContextHolder
import java.io.IOException

class AuthoritiesLoggingAfterFilter : Filter {

    private val log = logger()

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain) {
        SecurityContextHolder.getContext()
            .authentication?.let { authenticated ->
                log.info("User ${authenticated.name}  is successfully authenticated and has the authorities ${authenticated.authorities}")
            }

        chain.doFilter(request, response)
    }
}
