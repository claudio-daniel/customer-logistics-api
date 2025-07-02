package com.nauta.api.logistics.customer.model.api.response

import com.nauta.api.logistics.customer.model.api.dto.OrderDTO

data class ContainerOrdersApiResponse(
    val containerName: String,
    val orders: Set<OrderDTO>
)