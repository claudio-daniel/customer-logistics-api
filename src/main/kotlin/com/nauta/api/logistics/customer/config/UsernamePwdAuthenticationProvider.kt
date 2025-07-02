package com.nauta.api.logistics.customer.config

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class UsernamePwdAuthenticationProvider(
    private val userDetailsService: UserDetailsService,
) : AuthenticationProvider {

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication {
        val username = authentication.name
        val userDetail = userDetailsService.loadUserByUsername(username)

        return UsernamePasswordAuthenticationToken(
            username,
            authentication.credentials.toString(),
            userDetail.authorities
        )
    }

    override fun supports(authentication: Class<*>): Boolean =
        UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)
}