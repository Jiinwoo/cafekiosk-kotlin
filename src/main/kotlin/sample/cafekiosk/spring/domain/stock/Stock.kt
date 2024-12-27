package sample.cafekiosk.spring.domain.stock

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import sample.cafekiosk.spring.domain.product.BaseEntity

@Entity
class Stock(
    productNumber: String,
    quantity: Int,
) : BaseEntity() {
    fun isQuantityLessThan(quantity: Int): Boolean {

        return this.quantity < quantity
    }

    fun deductQuantity(quantity: Int) {
        if (isQuantityLessThan(quantity)) {
            throw IllegalArgumentException("차감할 재고 수량이 없습니다.")
        }

        this.quantity -= quantity
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    var productNumber: String = productNumber
        protected set
    var quantity: Int = quantity
        protected set

    companion object {
        fun of(productNumber: String, quantity: Int): Stock {
            return Stock(productNumber, quantity)
        }
    }

}
