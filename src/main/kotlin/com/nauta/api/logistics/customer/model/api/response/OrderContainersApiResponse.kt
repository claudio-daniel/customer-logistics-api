package com.nauta.api.logistics.customer.model.api.response

import com.nauta.api.logistics.customer.model.api.dto.ContainerDTO

data class OrderContainersApiResponse(
    val purchaseId: String,
    val containers: Set<ContainerDTO>
)