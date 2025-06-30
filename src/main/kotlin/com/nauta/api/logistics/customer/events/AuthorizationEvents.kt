package com.nauta.api.logistics.customer.events

import com.nauta.api.logistics.customer.util.logger
import org.springframework.context.event.EventListener
import org.springframework.security.authorization.event.AuthorizationDeniedEvent
import org.springframework.stereotype.Component

@Component
class AuthorizationEvents {

    private val log = logger()

    @EventListener
    fun onFailure(deniedEvent: AuthorizationDeniedEvent<*>) {
        log.error("Authorization failed for the user : ${deniedEvent.authentication.get().name} due to : ${deniedEvent.authorizationResult.toString()}")
    }
}