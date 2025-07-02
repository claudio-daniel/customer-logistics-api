package com.nauta.api.logistics.customer.model.api.request

import com.nauta.api.logistics.customer.model.api.dto.ContainerDTO
import com.nauta.api.logistics.customer.model.api.dto.OrderDTO

data class RegisterBookingApiRequest(
    val booking: String,
    val containers: List<ContainerDTO>?,
    val orders: List<OrderDTO>?
)