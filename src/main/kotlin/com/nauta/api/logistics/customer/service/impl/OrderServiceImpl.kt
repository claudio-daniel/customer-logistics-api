package com.nauta.api.logistics.customer.service.impl

import com.nauta.api.logistics.customer.exceptionhandling.api.error.ApiError
import com.nauta.api.logistics.customer.exceptionhandling.exception.CustomerLogisticDataNotFoundException
import com.nauta.api.logistics.customer.model.api.response.CustomerOrdersApiResponse
import com.nauta.api.logistics.customer.model.api.response.OrderContainersApiResponse
import com.nauta.api.logistics.customer.model.mapper.toContainerDTOs
import com.nauta.api.logistics.customer.model.mapper.toOrderDTOs
import com.nauta.api.logistics.customer.repository.OrderRepository
import com.nauta.api.logistics.customer.service.CustomerService
import com.nauta.api.logistics.customer.service.OrderService
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val customerService: CustomerService
) : OrderService {
    override fun findOrdersByCustomer(): CustomerOrdersApiResponse {
        val customerDTO = customerService.findAuthorizedCustomer()

        val ordersByCustomer = orderRepository.findByBookingCustomerId(customerDTO.customerId)

        return CustomerOrdersApiResponse(ordersByCustomer.toOrderDTOs())
    }

    override fun findOrderContainersByPurchaseAndCustomer(purchaseId: String): OrderContainersApiResponse {
        val customerDTO = customerService.findAuthorizedCustomer()

        return orderRepository.findByPurchaseAndBookingCustomerId(purchaseId, customerDTO.customerId)
            ?.let { registeredOrder ->
                OrderContainersApiResponse(
                    purchaseId = registeredOrder.purchase,
                    containers = registeredOrder.booking!!.containers.toContainerDTOs()
                )
            } ?: throw CustomerLogisticDataNotFoundException(
            apiError = ApiError(
                message = "Orders by purchase id not found",
                code = "ERR-003"
            )
        )
    }
}