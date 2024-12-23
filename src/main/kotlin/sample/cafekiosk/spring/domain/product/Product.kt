package sample.cafekiosk.spring.domain.product

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Product(
    productNumber: String,
    productType: ProductType,
    sellingStatus: ProductSellingStatus,
    name: String,
    price: Int,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    var productNumber: String = productNumber
        protected set

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    var productType: ProductType = productType
        protected set

    @Enumerated(EnumType.STRING)
    var sellingStatus: ProductSellingStatus = sellingStatus
        protected set

    var name: String = name
        protected set

    var price: Int = price
        protected set
}
