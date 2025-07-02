package com.nauta.api.logistics.customer.controller

import com.nauta.api.logistics.customer.mock.CONTAINER_DATA_BASE_URL
import com.nauta.api.logistics.customer.mock.CONTAINER_ID_MOCK
import com.nauta.api.logistics.customer.mock.CONTAINER_ORDES_URL
import com.nauta.api.logistics.customer.mock.TEST_USERNAME
import com.nauta.api.logistics.customer.mock.TEST_USER_ROLES
import com.nauta.api.logistics.customer.mock.mockRegisterCustomerLogisticDataException
import com.nauta.api.logistics.customer.mock.mockValidContainersApiResponse
import com.nauta.api.logistics.customer.mock.TOKEN
import com.nauta.api.logistics.customer.mock.mockValidContainerOrdersApiResponse
import com.nauta.api.logistics.customer.model.constants.SecurityConstants.AUTHORIZATION_HEADER
import com.nauta.api.logistics.customer.service.ContainerService
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
@WebMvcTest(ContainerController::class)
class ContainerControllerTest(
    @Autowired val mockMvc: MockMvc
) {

    @MockkBean
    private lateinit var containerService: ContainerService

    @Test
    @WithMockUser(username = TEST_USERNAME, roles = [TEST_USER_ROLES])
    fun `should return all customer containers`() {

        every { containerService.findContainersByCustomer() } returns mockValidContainersApiResponse()

        mockMvc.get(CONTAINER_DATA_BASE_URL) {
            header(AUTHORIZATION_HEADER, TOKEN)
        }.andExpect {
            status { isOk() }
        }

        verify(exactly = 1) { containerService.findContainersByCustomer() }
    }

    @Test
    @WithMockUser(username = TEST_USERNAME, roles = [TEST_USER_ROLES])
    fun `when service fails to find data should return server error`() {
        every { containerService.findContainersByCustomer() } throws mockRegisterCustomerLogisticDataException()

        mockMvc.get(CONTAINER_DATA_BASE_URL) {
            header(AUTHORIZATION_HEADER, TOKEN)
        }.andExpect { status { isInternalServerError() } }

        verify(exactly = 1) { containerService.findContainersByCustomer() }
    }

    @Test
    fun `when request does not have authorization for containers should return unauthorized`() {
        mockMvc.get(CONTAINER_DATA_BASE_URL)
            .andExpect { status { isUnauthorized() } }

        verify(exactly = 0) { containerService.findContainersByCustomer() }
    }

    @Test
    @WithMockUser(username = TEST_USERNAME, roles = [TEST_USER_ROLES])
    fun `should return all orders by a registered container`() {
        every { containerService.findContainerOrdersByContainerIdAndCustomer(CONTAINER_ID_MOCK) } returns mockValidContainerOrdersApiResponse()

        mockMvc.get("$CONTAINER_DATA_BASE_URL/$CONTAINER_ID_MOCK/$CONTAINER_ORDES_URL") {
            header(AUTHORIZATION_HEADER, TOKEN)
        }.andExpect { status { isOk() } }

        verify(exactly = 1) { containerService.findContainerOrdersByContainerIdAndCustomer(CONTAINER_ID_MOCK) }
    }

    @Test
    @WithMockUser(username = TEST_USERNAME, roles = [TEST_USER_ROLES])
    fun `when services failsto find container orders data should return internal error`() {
        every { containerService.findContainerOrdersByContainerIdAndCustomer(CONTAINER_ID_MOCK) } throws mockRegisterCustomerLogisticDataException()

        mockMvc.get("$CONTAINER_DATA_BASE_URL/$CONTAINER_ID_MOCK/$CONTAINER_ORDES_URL") {
            header(AUTHORIZATION_HEADER, TOKEN)
        }.andExpect { status { isInternalServerError() } }

        verify(exactly = 1) { containerService.findContainerOrdersByContainerIdAndCustomer(CONTAINER_ID_MOCK) }
    }

    @Test
    fun `when request does not have authorization for container orders should return unauthorized`() {
        mockMvc.get("$CONTAINER_DATA_BASE_URL/$CONTAINER_ID_MOCK/$CONTAINER_ORDES_URL")
            .andExpect { status { isUnauthorized() } }

        verify(exactly = 0) { containerService.findContainerOrdersByContainerIdAndCustomer(CONTAINER_ID_MOCK) }
    }
}