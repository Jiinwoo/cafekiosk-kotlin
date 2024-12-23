package sample.cafekiosk.spring.api.service.order.response

import org.springframework.data.jpa.domain.AbstractPersistable_.id
import sample.cafekiosk.spring.api.service.product.response.ProductResponse
import sample.cafekiosk.spring.domain.order.Order
import java.time.LocalDateTime

class OrderResponse private constructor(
    val id: Long,
    val totalPrice: Int,
    val registeredDateTime: LocalDateTime,
    val orderProducts: List<ProductResponse>,
) {
    companion object {
        fun of(order: Order): OrderResponse =
            OrderResponse(
                id = order.id,
                totalPrice = order.totalPrice,
                registeredDateTime = order.registeredDateTime,
                orderProducts = order.orderProducts.map { ProductResponse.of(it.product) },
            )
    }
}
