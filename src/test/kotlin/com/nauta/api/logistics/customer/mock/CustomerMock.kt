package com.nauta.api.logistics.customer.mock

import com.nauta.api.logistics.customer.model.api.dto.CustomerDTO

const val TEST_USERNAME = "testUser"
const val CUSTOMER_ID_MOCK = 1234L

fun mockCustomerDTO(): CustomerDTO = CustomerDTO(username = TEST_USERNAME, customerId = CUSTOMER_ID_MOCK)