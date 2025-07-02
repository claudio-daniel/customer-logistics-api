package com.nauta.api.logistics.customer.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.ZonedDateTime

@Entity
@Table(name = "invoices")
class InvoiceEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long?,

    @Column(name = "invoice", nullable = false)
    val invoice: String,

    @Column(name = "created_at")
    val createdAt: ZonedDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    var order: OrderEntity?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is InvoiceEntity) return false
        return this.invoice == other.invoice
    }

    override fun hashCode(): Int = invoice.hashCode()
}