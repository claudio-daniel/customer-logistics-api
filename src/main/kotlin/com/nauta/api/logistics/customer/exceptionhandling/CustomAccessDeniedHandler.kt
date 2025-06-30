package com.nauta.api.logistics.customer.exceptionhandling

import com.nauta.api.logistics.customer.model.constants.SecurityConstants
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import java.io.IOException
import java.time.LocalDateTime

class CustomAccessDeniedHandler : AccessDeniedHandler {

    @Throws(IOException::class, ServletException::class)
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException?
    ) {
        response.setHeader(SecurityConstants.AUTHORIZATION_ERROR, SecurityConstants.AUTHORIZATION_FAILED_MESSAGE)
        response.status = HttpStatus.FORBIDDEN.value()
        response.contentType = SecurityConstants.HTTP_CONTENT_TYPE
        response.writer.write(buildJsonResponse(request, accessDeniedException))
    }

    fun buildJsonResponse(request: HttpServletRequest, accessDeniedException: AccessDeniedException?): String {
        val currentTimeStamp = LocalDateTime.now()
        val path = request.requestURI
        val message = buildMessage(accessDeniedException)

        return """"{
                "timestamp": $currentTimeStamp,
                 "status": ${HttpStatus.FORBIDDEN.value()}, 
                 "error": ${HttpStatus.FORBIDDEN.reasonPhrase}, 
                 "message": $message, 
                 "path": $path
                 }"""
    }

    fun buildMessage(accessDeniedException: AccessDeniedException?): String? {
        return if (accessDeniedException != null && accessDeniedException.message != null)
            accessDeniedException.message
        else "Authorization failed"
    }
}