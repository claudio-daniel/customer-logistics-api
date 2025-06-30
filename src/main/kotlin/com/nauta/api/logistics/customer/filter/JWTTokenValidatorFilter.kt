package com.nauta.api.logistics.customer.filter

import com.nauta.api.logistics.customer.model.constants.SecurityConstants
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.nio.charset.StandardCharsets
import javax.crypto.SecretKey

class JWTTokenValidatorFilter : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            request.getHeader(SecurityConstants.JWT_HEADER)?.let { jwt ->
                getSecretKey()?.let { secretKey ->
                    SecurityContextHolder.getContext().authentication = buildAuthentication(jwt, secretKey)
                }
            }

        } catch (_: Exception) {
            throw BadCredentialsException("Invalid Token received!")
        }

        filterChain.doFilter(request, response)
    }

    fun getSecretKey(): SecretKey? {
        val secret = environment.getProperty(
            SecurityConstants.JWT_SECRET_KEY, SecurityConstants.JWT_SECRET_DEFAULT_VALUE
        )
        return Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
    }

    fun buildAuthentication(jwt: String, secretKey: SecretKey): UsernamePasswordAuthenticationToken {
        val claims = Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(jwt)
            .getPayload()

        val username = claims[SecurityConstants.USER_CLAIM].toString()

        val authorities = claims[SecurityConstants.AUTHORITIES_CLAIM].toString()

        return UsernamePasswordAuthenticationToken(
            username, null,
            AuthorityUtils.commaSeparatedStringToAuthorityList(authorities)
        )
    }

    @Throws(ServletException::class)
    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return request.servletPath == SecurityConstants.USER_ENDPOINT
    }
}
