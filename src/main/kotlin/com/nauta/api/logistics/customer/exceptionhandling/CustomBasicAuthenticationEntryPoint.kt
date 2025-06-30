package com.nauta.api.logistics.customer.exceptionhandling

import com.nauta.api.logistics.customer.model.constants.SecurityConstants
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import java.io.IOException
import java.time.LocalDateTime

class CustomBasicAuthenticationEntryPoint : AuthenticationEntryPoint {

    @Throws(IOException::class, ServletException::class)
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException?
    ) {
        val jsonResponse = buildJsonResponse(request, authException)

        response.setHeader(SecurityConstants.AUTHENTICATION_ERROR, SecurityConstants.AUTHENTICATION_FAILED_MESSAGE)
        response.status = HttpStatus.UNAUTHORIZED.value()
        response.contentType = SecurityConstants.HTTP_CONTENT_TYPE
        response.writer.write(jsonResponse)
    }

    fun buildJsonResponse(request: HttpServletRequest, authException: AuthenticationException?): String {
        val currentTimeStamp = LocalDateTime.now()
        val path = request.requestURI
        val message = buildMessage(authException)

        return """"{
                "timestamp": $currentTimeStamp,
                 "status": ${HttpStatus.FORBIDDEN.value()}, 
                 "error": ${HttpStatus.FORBIDDEN.reasonPhrase}, 
                 "message": $message, 
                 "path": $path
                 }"""
    }

    fun buildMessage(authException: AuthenticationException?): String? {
        return if (authException != null && authException.message != null)
            authException.message
        else "Unauthorized"
    }
}