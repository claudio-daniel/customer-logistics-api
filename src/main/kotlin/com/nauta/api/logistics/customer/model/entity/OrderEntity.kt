package com.nauta.api.logistics.customer.model.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.ZonedDateTime

@Entity
@Table(name = "orders")
class OrderEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long?,

    @Column(name = "purchase")
    val purchase: String,

    @Column(name = "created_at")
    val createdAt: ZonedDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    var booking: BookingEntity?,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    val invoices: MutableList<InvoiceEntity>,
) {

    fun loadAssociations() {
        this.invoices.forEach { invoice -> invoice.order = this }
    }

    fun addInvoices(invoices: MutableList<InvoiceEntity>) {
        this.invoices.addAll(invoices)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OrderEntity) return false
        return this.purchase == other.purchase
    }

    override fun hashCode(): Int = purchase.hashCode()
}