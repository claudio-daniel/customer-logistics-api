package com.nauta.api.logistics.customer.model.api.response

import com.nauta.api.logistics.customer.model.api.dto.OrderDTO

data class CustomerOrdersApiResponse(
    val orders: Set<OrderDTO>
)