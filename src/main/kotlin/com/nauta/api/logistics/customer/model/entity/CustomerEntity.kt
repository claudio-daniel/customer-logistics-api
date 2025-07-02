package com.nauta.api.logistics.customer.model.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.sql.Date

@Entity
@Table(name = "customers")
class CustomerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    var id: Long?,

    val name: String,

    val email: String,

    @Column(name = "mobile_number")
    val mobileNumber: String,

    var pwd: String,

    val role: String,

    @Column(name = "create_dt")
    var createdDate: Date?,

    @OneToMany(mappedBy = "customer", cascade = [CascadeType.ALL])
    val authorities: MutableSet<AuthorityEntity>?
) {
    fun loadAssociations(): CustomerEntity {
        authorities?.forEach { authority -> authority.customer = this }

        return this
    }

    fun verifyId(): Boolean {
        return this.id != null && this.id!! > 0
    }
}