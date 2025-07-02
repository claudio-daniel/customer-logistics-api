package com.nauta.api.logistics.customer.filter

import com.nauta.api.logistics.customer.model.constants.SecurityConstants
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.Date

@Component
class JWTTokenGeneratorFilter : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        SecurityContextHolder.getContext().authentication?.let { authenticated ->
            if (authenticated.isAuthenticated) {
                response.setHeader(SecurityConstants.JWT_HEADER, generateJWT(authenticated))
            }
        }

        filterChain.doFilter(request, response)
    }

    fun generateJWT(authentication: Authentication): String {
        val secret = environment.getProperty(
            SecurityConstants.JWT_SECRET_KEY,
            SecurityConstants.JWT_SECRET_DEFAULT_VALUE
        )
        val secretKey = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))

        return Jwts.builder()
            .issuer(SecurityConstants.JWT_ISSUER)
            .subject(SecurityConstants.JWT_SUBJECT)
            .claim(SecurityConstants.USER_CLAIM, authentication.name)
            .claim(SecurityConstants.AUTHORITIES_CLAIM, authentication.authorities.joinToString(",") { it.authority })
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + SecurityConstants.JWT_EXPIRATION))
            .signWith(secretKey)
            .compact()
    }

    @Throws(ServletException::class)
    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return request.servletPath != SecurityConstants.USER_ENDPOINT
    }
}