package com.nauta.api.logistics.customer.controller

import com.nauta.api.logistics.customer.mock.ORDERS_DATA_BASE_URL
import com.nauta.api.logistics.customer.mock.ORDER_CONTAINERS_URL
import com.nauta.api.logistics.customer.mock.PURCHASE_ID_MOCK
import com.nauta.api.logistics.customer.mock.TEST_USERNAME
import com.nauta.api.logistics.customer.mock.TEST_USER_ROLES
import com.nauta.api.logistics.customer.mock.TOKEN
import com.nauta.api.logistics.customer.mock.mockRegisterCustomerLogisticDataException
import com.nauta.api.logistics.customer.mock.mockValidOrderContainersApiResponse
import com.nauta.api.logistics.customer.mock.mockValidOrdersApiResponse
import com.nauta.api.logistics.customer.model.constants.SecurityConstants.AUTHORIZATION_HEADER
import com.nauta.api.logistics.customer.service.OrderService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@AutoConfigureMockMvc
@WebMvcTest(OrderController::class)
class OrderControllerTest(
    @Autowired val mockMvc: MockMvc
) {

    @MockkBean
    private lateinit var orderService: OrderService

    @Test
    @WithMockUser(username = TEST_USERNAME, roles = [TEST_USER_ROLES])
    fun `should return all customer orders`() {

        every { orderService.findOrdersByCustomer() } returns mockValidOrdersApiResponse()

        mockMvc.get(ORDERS_DATA_BASE_URL) {
            header(AUTHORIZATION_HEADER, TOKEN)
        }.andExpect {
            status { isOk() }
        }

        verify(exactly = 1) { orderService.findOrdersByCustomer() }
    }

    @Test
    @WithMockUser(username = TEST_USERNAME, roles = [TEST_USER_ROLES])
    fun `when service fails to find data should return server error`() {
        every { orderService.findOrdersByCustomer() } throws mockRegisterCustomerLogisticDataException()

        mockMvc.get(ORDERS_DATA_BASE_URL) {
            header(AUTHORIZATION_HEADER, TOKEN)
        }.andExpect { status { isInternalServerError() } }

        verify(exactly = 1) { orderService.findOrdersByCustomer() }
    }

    @Test
    fun `when request does not have authorization for orders should return unauthorized`() {
        mockMvc.get(ORDERS_DATA_BASE_URL)
            .andExpect { status { isUnauthorized() } }

        verify(exactly = 0) { orderService.findOrdersByCustomer() }
    }

    @Test
    @WithMockUser(username = TEST_USERNAME, roles = [TEST_USER_ROLES])
    fun `should return all containers by a registered order`() {
        every { orderService.findOrderContainersByPurchaseAndCustomer(PURCHASE_ID_MOCK) } returns mockValidOrderContainersApiResponse()

        mockMvc.get("$ORDERS_DATA_BASE_URL/$PURCHASE_ID_MOCK/$ORDER_CONTAINERS_URL") {
            header(AUTHORIZATION_HEADER, TOKEN)
        }.andExpect { status { isOk() } }

        verify(exactly = 1) { orderService.findOrderContainersByPurchaseAndCustomer(PURCHASE_ID_MOCK) }
    }

    @Test
    @WithMockUser(username = TEST_USERNAME, roles = [TEST_USER_ROLES])
    fun `when service fails to find order containers data should return internal error`() {
        every { orderService.findOrderContainersByPurchaseAndCustomer(PURCHASE_ID_MOCK) } throws mockRegisterCustomerLogisticDataException()

        mockMvc.get("$ORDERS_DATA_BASE_URL/$PURCHASE_ID_MOCK/$ORDER_CONTAINERS_URL") {
            header(AUTHORIZATION_HEADER, TOKEN)
        }.andExpect { status { isInternalServerError() } }

        verify(exactly = 1) { orderService.findOrderContainersByPurchaseAndCustomer(PURCHASE_ID_MOCK) }
    }

    @Test
    fun `when request does not have authorization for order containers should return unauthorized`() {
        mockMvc.get("$ORDERS_DATA_BASE_URL/$PURCHASE_ID_MOCK/$ORDER_CONTAINERS_URL")
            .andExpect { status { isUnauthorized() } }

        verify(exactly = 0) { orderService.findOrderContainersByPurchaseAndCustomer(PURCHASE_ID_MOCK) }
    }
}