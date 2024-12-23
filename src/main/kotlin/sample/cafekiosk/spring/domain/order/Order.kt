package sample.cafekiosk.spring.domain.order

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import sample.cafekiosk.spring.domain.product.BaseEntity
import sample.cafekiosk.spring.domain.product.Product
import java.time.LocalDateTime

@Table(name = "orders")
@Entity
class Order(
    products: MutableList<Product>,
    registeredDateTime: LocalDateTime,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.INIT
        protected set

    var totalPrice: Int = calculateTotalPrice(products)
        protected set

    var registeredDateTime: LocalDateTime = registeredDateTime
        protected set

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    var orderProducts: MutableList<OrderProduct> =
        products
            .map {
                OrderProduct(
                    order = this,
                    product = it,
                )
            }.toMutableList()
        protected set

    companion object {
        fun create(
            products: MutableList<Product>,
            registeredDate: LocalDateTime,
        ): Order =
            Order(
                products = products,
                registeredDateTime = registeredDate,
            )

        private fun calculateTotalPrice(products: List<Product>) = products.sumOf { it.price }
    }
}
