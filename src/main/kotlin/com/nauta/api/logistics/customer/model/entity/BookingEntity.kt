package com.nauta.api.logistics.customer.model.entity

import com.nauta.api.logistics.customer.model.mapper.addAllContainerEntities
import com.nauta.api.logistics.customer.model.mapper.addAllOrderEntities
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.ZonedDateTime

@Entity
@Table(name = "bookings")
class BookingEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long?,

    @Column(name = "code")
    val code: String,

    @Column(name = "customer_id")
    val customerId: Long,

    @Column(name = "created_at")
    val createdAt: ZonedDateTime,

    @OneToMany(mappedBy = "booking", cascade = [CascadeType.ALL])
    val containers: MutableList<ContainerEntity>,

    @OneToMany(mappedBy = "booking", cascade = [CascadeType.ALL])
    val orders: MutableList<OrderEntity>
) {
    fun loadAssociations(): BookingEntity {
        loadContainers()
        loadOrders()
        return this
    }

    fun loadContainers() {
        this.containers.forEach { container -> container.booking = this }
    }

    fun loadOrders() {
        this.orders.forEach { order ->
            order.loadAssociations()
            order.booking = this
        }
    }

    fun refreshContainers(newContainers: MutableSet<ContainerEntity>) {
        this.containers.addAllContainerEntities(newContainers)
    }

    fun refreshOrders(newOrders: MutableSet<OrderEntity>) {
        this.orders.addAllOrderEntities(newOrders)
    }
}