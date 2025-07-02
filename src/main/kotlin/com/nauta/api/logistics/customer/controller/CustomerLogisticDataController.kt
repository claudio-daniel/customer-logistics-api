package com.nauta.api.logistics.customer.controller

import com.nauta.api.logistics.customer.model.api.request.RegisterBookingApiRequest
import com.nauta.api.logistics.customer.model.api.response.RegisteredBookingApiResponse
import com.nauta.api.logistics.customer.model.entity.BookingEntity
import com.nauta.api.logistics.customer.service.BookingService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/email")
class CustomerLogisticDataController(
    private val bookingService: BookingService<BookingEntity, RegisterBookingApiRequest>
) {
    @Operation(summary = "Register customer logistics data")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Register Done"),
            ApiResponse(responseCode = "400", description = "Bad Request"),
            ApiResponse(responseCode = "401", description = "Unauthorized"),
            ApiResponse(responseCode = "403", description = "Forbidden"),
            ApiResponse(responseCode = "500", description = "Server Error")
        ]
    )
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    fun registerCustomerLogisticData(@RequestBody bookingApiRequest: RegisterBookingApiRequest): RegisteredBookingApiResponse {
        return bookingService.registerBooking(bookingApiRequest)
    }
}