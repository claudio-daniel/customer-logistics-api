package com.nauta.api.logistics.customer.events

import com.nauta.api.logistics.customer.util.logger
import org.springframework.context.event.EventListener
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.stereotype.Component

@Component
class AuthenticationEvents {

    private val log = logger()

    @EventListener
    fun onSuccess(successEvent: AuthenticationSuccessEvent) {
        log.info("Login successful for the user : ${successEvent.authentication.name}")
    }

    @EventListener
    fun onFailure(failureEvent: AbstractAuthenticationFailureEvent) {
        log.error(
            "Login failed for the user : ${failureEvent.authentication.name} due to : ${failureEvent.exception.message}"
        )
    }
}