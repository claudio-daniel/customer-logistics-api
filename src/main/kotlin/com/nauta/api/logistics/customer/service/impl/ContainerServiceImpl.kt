package com.nauta.api.logistics.customer.service.impl

import com.nauta.api.logistics.customer.exceptionhandling.api.error.ApiError
import com.nauta.api.logistics.customer.exceptionhandling.exception.CustomerLogisticDataNotFoundException
import com.nauta.api.logistics.customer.model.api.response.ContainerOrdersApiResponse
import com.nauta.api.logistics.customer.model.api.response.CustomerContainersApiResponse
import com.nauta.api.logistics.customer.model.mapper.toContainerDTOs
import com.nauta.api.logistics.customer.model.mapper.toOrderDTOs
import com.nauta.api.logistics.customer.repository.ContainerRepository
import com.nauta.api.logistics.customer.service.ContainerService
import com.nauta.api.logistics.customer.service.CustomerService
import org.springframework.stereotype.Service

@Service
class ContainerServiceImpl(
    private val containerRepository: ContainerRepository,
    private val customerService: CustomerService
) : ContainerService {
    override fun findContainersByCustomer(): CustomerContainersApiResponse {
        val customerDTO = customerService.findAuthorizedCustomer()

        val containersByCustomer = containerRepository.findByBookingCustomerId(customerDTO.customerId)

        return CustomerContainersApiResponse(containersByCustomer.toContainerDTOs())
    }

    override fun findContainerOrdersByContainerIdAndCustomer(containerId: Long): ContainerOrdersApiResponse {
        val customerDTO = customerService.findAuthorizedCustomer()

        return containerRepository.findByIdAndBookingCustomerId(containerId, customerDTO.customerId)
            ?.let { containersByCustomer ->
                ContainerOrdersApiResponse(
                    containerName = containersByCustomer.name,
                    orders = containersByCustomer.booking!!.orders.toOrderDTOs()
                )
            } ?: throw CustomerLogisticDataNotFoundException(
            apiError = ApiError(
                message = "Containers by id not found",
                code = "ERR-002"
            )
        )
    }
}