package com.nauta.api.logistics.customer.model.mapper

import com.nauta.api.logistics.customer.model.api.dto.InvoiceDTO
import com.nauta.api.logistics.customer.model.entity.InvoiceEntity
import java.time.ZonedDateTime

fun toNewInvoiceEntity(invoiceApiRequest: InvoiceDTO): InvoiceEntity = InvoiceEntity(
    id = null,
    invoice = invoiceApiRequest.invoice,
    createdAt = ZonedDateTime.now(),
    order = null
)

fun toInvoiceResponse(invoiceEntity: InvoiceEntity): InvoiceDTO = InvoiceDTO(invoice = invoiceEntity.invoice)

fun Set<InvoiceDTO>.toInvoiceEntities(): MutableList<InvoiceEntity> = this.map { toNewInvoiceEntity(it) }
    .toMutableList()

fun MutableList<InvoiceEntity>.toInvoiceDTOs(): Set<InvoiceDTO> = this.map { toInvoiceResponse(it) }
    .toSet()