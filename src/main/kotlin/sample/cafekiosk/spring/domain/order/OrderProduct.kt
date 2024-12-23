package sample.cafekiosk.spring.domain.order

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import sample.cafekiosk.spring.domain.product.BaseEntity
import sample.cafekiosk.spring.domain.product.Product

@Entity
class OrderProduct(
    order: Order,
    product: Product,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @ManyToOne(fetch = FetchType.LAZY)
    var order: Order = order
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    var product: Product = product
        protected set
}
