package sample.cafekiosk.spring.domain.order

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import sample.cafekiosk.spring.domain.product.Product
import sample.cafekiosk.spring.domain.product.ProductSellingStatus
import sample.cafekiosk.spring.domain.product.ProductType
import java.time.LocalDateTime

class OrderTest {
    @Test
    fun `상품 리스트에서 주문의 총 금액을 계산한다`() {
        // given
        val productList =
            mutableListOf(
                createProduct("001", 1000),
                createProduct("002", 3000),
                createProduct("003", 5000),
            )
        // when
        val order = Order.create(productList, LocalDateTime.now())
        // then
        assertThat(order.totalPrice).isEqualTo(9000)
    }

    @Test
    fun `주문 생성 시 주문 등록 시간을 기록한다`() {
        // given
        val productList =
            mutableListOf(
                createProduct("001", 1000),
                createProduct("002", 3000),
                createProduct("003", 5000),
            )
        val registeredDateTime = LocalDateTime.now()
        // when
        val order = Order.create(productList, registeredDateTime)
        // then
        assertThat(order.status).isEqualByComparingTo(OrderStatus.INIT)
        assertThat(order.registeredDateTime).isEqualTo(registeredDateTime)
    }

    private fun createProduct(
        productNumber: String,
        price: Int,
    ): Product =
        Product(
            productNumber = productNumber,
            productType = ProductType.HANDMADE,
            sellingStatus = ProductSellingStatus.HOLD,
            name = "카페라떼",
            price = price,
        )
}
