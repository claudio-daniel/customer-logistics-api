package com.nauta.api.logistics.customer.model.api.request

import com.fasterxml.jackson.annotation.JsonProperty

data class RegisterCustomerRequest(
    val name: String,

    val email: String,

    val mobileNumber: String,

    val role: String,

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var pwd: String,
)