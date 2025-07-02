package com.nauta.api.logistics.customer.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.nauta.api.logistics.customer.config.SecurityConfigTest
import com.nauta.api.logistics.customer.mock.CUSTOMER_LOGISTIC_DATA_BASE_URL
import com.nauta.api.logistics.customer.mock.mockRegisterCustomerLogisticDataException
import com.nauta.api.logistics.customer.mock.mockResponseOK
import com.nauta.api.logistics.customer.mock.mockValidRequestBody
import com.nauta.api.logistics.customer.mock.mockValidRequestWithOnlyContainer
import com.nauta.api.logistics.customer.mock.mockValidRequestWithOnlyOrders
import com.nauta.api.logistics.customer.model.api.request.RegisterBookingApiRequest
import com.nauta.api.logistics.customer.model.entity.BookingEntity
import com.nauta.api.logistics.customer.service.BookingService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.patch

@AutoConfigureMockMvc
@WebMvcTest(CustomerLogisticDataController::class)
@Import(SecurityConfigTest::class)
class CustomerLogisticDataControllerTest(
    @Autowired val mockMvc: MockMvc
) {

    @MockkBean
    lateinit var bookingService: BookingService<BookingEntity, RegisterBookingApiRequest>

    @Test
    fun `should register customer logistic data with valid request`() {
        val request = mockValidRequestBody()
        val expectedResponse = mockResponseOK()

        every { bookingService.registerBooking(request) } returns expectedResponse

        mockMvc.patch(CUSTOMER_LOGISTIC_DATA_BASE_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(request)
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun `should accept requests with only containers for customer logistic registration`() {
        val request = mockValidRequestWithOnlyContainer()
        val expectedResponse = mockResponseOK()

        every { bookingService.registerBooking(request) } returns expectedResponse

        mockMvc.patch(CUSTOMER_LOGISTIC_DATA_BASE_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(request)
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun `should accept requests with only orders for customer logistic registration`() {
        val request = mockValidRequestWithOnlyOrders()
        val expectedResponse = mockResponseOK()

        every { bookingService.registerBooking(request) } returns expectedResponse

        mockMvc.patch(CUSTOMER_LOGISTIC_DATA_BASE_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(request)
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun `should internal error when service failed to save customer logistic data`() {
        val request = mockValidRequestBody()

        every { bookingService.registerBooking(request) } throws mockRegisterCustomerLogisticDataException()

        mockMvc.patch(CUSTOMER_LOGISTIC_DATA_BASE_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(request)
        }.andExpect {
            status { isInternalServerError() }
        }
    }

    @Test
    fun `should return bad request whit invalid request`() {

        every { bookingService.registerBooking(mockValidRequestBody()) } returns mockResponseOK()

        mockMvc.patch(CUSTOMER_LOGISTIC_DATA_BASE_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isBadRequest() }
        }
    }
}