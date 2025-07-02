package com.nauta.api.logistics.customer.controller

import com.nauta.api.logistics.customer.model.api.response.CustomerOrdersApiResponse
import com.nauta.api.logistics.customer.model.api.response.OrderContainersApiResponse
import com.nauta.api.logistics.customer.service.OrderService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/orders")
class OrderController(
    private val orderService: OrderService
) {
    @Operation(summary = "Returns all orders registered by a customer.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Find Orders Done"),
            ApiResponse(responseCode = "401", description = "Unauthorized"),
            ApiResponse(responseCode = "401", description = "Forbidden"),
            ApiResponse(responseCode = "500", description = "Server Error")
        ]
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    fun getAllContainers(): CustomerOrdersApiResponse {
        return orderService.findOrdersByCustomer()
    }

    @Operation(summary = "Returns all order containers registered by a customer.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Find Order Containers Done"),
            ApiResponse(responseCode = "401", description = "Unauthorized"),
            ApiResponse(responseCode = "401", description = "Forbidden"),
            ApiResponse(responseCode = "500", description = "Server Error")
        ]
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{purchaseId}/containers")
    fun getAllContainerOrders(@PathVariable("purchaseId") purchaseId: String): OrderContainersApiResponse {
        return orderService.findOrderContainersByPurchaseAndCustomer(purchaseId)
    }
}