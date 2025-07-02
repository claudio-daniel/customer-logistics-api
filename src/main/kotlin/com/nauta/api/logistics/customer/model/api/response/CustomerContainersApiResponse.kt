package com.nauta.api.logistics.customer.model.api.response

import com.nauta.api.logistics.customer.model.api.dto.ContainerDTO

data class CustomerContainersApiResponse(
    val containers: Set<ContainerDTO>,
)