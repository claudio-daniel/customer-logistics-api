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
@Table(name = "containers")
class ContainerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long?,

    @Column(name = "name")
    val name: String,

    @Column(name = "created_at")
    val createdAt: ZonedDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    var booking: BookingEntity?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ContainerEntity) return false
        return this.name == other.name
    }

    override fun hashCode(): Int = name.hashCode()
}