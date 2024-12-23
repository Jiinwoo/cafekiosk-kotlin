package sample.cafekiosk.spring.api.service.order

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest
import sample.cafekiosk.spring.domain.product.Product
import sample.cafekiosk.spring.domain.product.ProductRepository
import sample.cafekiosk.spring.domain.product.ProductSellingStatus
import sample.cafekiosk.spring.domain.product.ProductType
import java.time.LocalDateTime

@ActiveProfiles("test")
@SpringBootTest
class OrderServiceTest(
    @Autowired
    private val orderService: OrderService,
    @Autowired
    private val productRepository: ProductRepository,
) {
    @Test
    fun `주문번호 리스트를 받아 주문을 생성한다`() {
        // given
        val product1 = createProduct(ProductType.HANDMADE, "001", 1000)
        val product2 = createProduct(ProductType.HANDMADE, "002", 3000)
        val product3 = createProduct(ProductType.HANDMADE, "003", 5000)
        productRepository.saveAll(listOf(product1, product2, product3))
        val req =
            OrderCreateRequest(
                productNumbers = listOf("001", "002"),
            )
        val registeredDateTime = LocalDateTime.now()
        // when
        val res =
            orderService.createOrder(
                req,
                registeredDateTime,
            )
        // then
        assertThat(res.id).isNotZero()
        assertThat(res)
            .extracting("registeredDateTime", "totalPrice")
            .contains(registeredDateTime, 4000)
        assertThat(res.orderProducts)
            .hasSize(2)
            .extracting("productNumber", "price")
            .containsExactlyInAnyOrder(
                tuple("001", 1000),
                tuple("002", 3000),
            )
    }

    private fun createProduct(
        type: ProductType,
        productNumber: String,
        price: Int,
    ): Product =
        Product(
            productNumber = productNumber,
            productType = type,
            sellingStatus = ProductSellingStatus.HOLD,
            name = "카페라떼",
            price = price,
        )
}
