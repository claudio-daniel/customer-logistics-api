package com.nauta.api.logistics.customer.service

import com.nauta.api.logistics.customer.model.api.response.ContainerOrdersApiResponse
import com.nauta.api.logistics.customer.model.api.response.CustomerContainersApiResponse

interface ContainerService {

    fun findContainersByCustomer(): CustomerContainersApiResponse

    fun findContainerOrdersByContainerIdAndCustomer(containerId: Long): ContainerOrdersApiResponse
}