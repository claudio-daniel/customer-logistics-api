package com.nauta.api.logistics.customer.model.api.dto

data class OrderDTO(
    val purchase: String,
    val invoices: Set<InvoiceDTO>
)