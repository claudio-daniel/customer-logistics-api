package com.nauta.api.logistics.customer.service

import com.nauta.api.logistics.customer.model.api.response.CustomerOrdersApiResponse
import com.nauta.api.logistics.customer.model.api.response.OrderContainersApiResponse

interface OrderService {

    fun findOrdersByCustomer(): CustomerOrdersApiResponse

    fun findOrderContainersByPurchaseAndCustomer(purchaseId: String): OrderContainersApiResponse
}