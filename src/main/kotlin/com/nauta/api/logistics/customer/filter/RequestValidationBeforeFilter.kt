package com.nauta.api.logistics.customer.filter

import com.nauta.api.logistics.customer.model.constants.SecurityConstants
import jakarta.servlet.*
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.util.StringUtils
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.*

class RequestValidationBeforeFilter : Filter {

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain) {
        val req = request as HttpServletRequest
        val res = response as HttpServletResponse

        req.getHeader(HttpHeaders.AUTHORIZATION)?.let { header ->
            try {
                val authorizationHeader = header.trim { it <= ' ' }

                validateAuthorizationHeader(authorizationHeader, res)

            } catch (_: IllegalArgumentException) {
                throw BadCredentialsException("Failed to decode basic authentication token")
            }
        }

        chain.doFilter(request, response)
    }

    fun validateAuthorizationHeader(authorizationHeader: String, res: HttpServletResponse) {
        if (StringUtils.startsWithIgnoreCase(authorizationHeader, SecurityConstants.BASIC_AUTHORIZATION_PREFIX)) {

            val base64Token = authorizationHeader.substring(6).toByteArray(StandardCharsets.UTF_8)

            val decoded = Base64.getDecoder().decode(base64Token)

            val token = String(decoded, StandardCharsets.UTF_8) // un:pwd

            val delim = token.indexOf(":")

            if (delim == -1) {
                throw BadCredentialsException("Invalid basic authentication token")
            }

            val email = token.substring(0, delim)

            if (email.lowercase(Locale.getDefault()).contains("test")) {
                res.status = HttpServletResponse.SC_BAD_REQUEST
                return
            }
        }
    }
}
