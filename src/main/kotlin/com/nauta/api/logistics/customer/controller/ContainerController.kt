package com.nauta.api.logistics.customer.controller

import com.nauta.api.logistics.customer.model.api.response.ContainerOrdersApiResponse
import com.nauta.api.logistics.customer.model.api.response.CustomerContainersApiResponse
import com.nauta.api.logistics.customer.service.ContainerService
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
@RequestMapping("/api/containers")
class ContainerController(
    private val containerService: ContainerService
) {
    @Operation(summary = "Returns all containers registered by a customer.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Find Containers Done"),
            ApiResponse(responseCode = "401", description = "Unauthorized"),
            ApiResponse(responseCode = "401", description = "Forbidden"),
            ApiResponse(responseCode = "500", description = "Server Error")
        ]
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    fun getAllContainers(): CustomerContainersApiResponse {
        return containerService.findContainersByCustomer()
    }

    @Operation(summary = "Returns all container orders registered by a customer.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Find Container Orders Done"),
            ApiResponse(responseCode = "401", description = "Unauthorized"),
            ApiResponse(responseCode = "401", description = "Forbidden"),
            ApiResponse(responseCode = "500", description = "Server Error")
        ]
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{containerId}/orders")
    fun getAllContainerOrders(@PathVariable("containerId") containerId: Long): ContainerOrdersApiResponse {
        return containerService.findContainerOrdersByContainerIdAndCustomer(containerId)
    }
}