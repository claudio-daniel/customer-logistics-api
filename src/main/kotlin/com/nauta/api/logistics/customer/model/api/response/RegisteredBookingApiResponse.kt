package com.nauta.api.logistics.customer.model.api.response

import com.nauta.api.logistics.customer.model.api.dto.ContainerDTO
import com.nauta.api.logistics.customer.model.api.dto.OrderDTO

data class RegisteredBookingApiResponse(
    val id: String,
    val booking: String,
    val containers: Set<ContainerDTO>?,
    val orders: Set<OrderDTO>?
)