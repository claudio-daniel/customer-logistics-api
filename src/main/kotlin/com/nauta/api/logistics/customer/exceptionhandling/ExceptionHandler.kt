package com.nauta.api.logistics.customer.exceptionhandling

import com.nauta.api.logistics.customer.exceptionhandling.api.error.ErrorApiResponse
import com.nauta.api.logistics.customer.exceptionhandling.exception.MicroserviceException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import java.time.ZonedDateTime

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class ExceptionHandler {

    @ExceptionHandler(MicroserviceException::class)
    fun handleException(
        microserviceException: MicroserviceException,
        request: WebRequest
    ): ResponseEntity<ErrorApiResponse> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ErrorApiResponse(
                message = microserviceException.localizedMessage,
                apiError = microserviceException.apiError,
                timestamp = ZonedDateTime.now()
            )
        )
    }
}