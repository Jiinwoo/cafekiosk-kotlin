package sample.cafekiosk.spring.api.service.order

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest
import sample.cafekiosk.spring.domain.order.OrderProductRepository
import sample.cafekiosk.spring.domain.order.OrderRepository
import sample.cafekiosk.spring.domain.product.Product
import sample.cafekiosk.spring.domain.product.ProductRepository
import sample.cafekiosk.spring.domain.product.ProductSellingStatus
import sample.cafekiosk.spring.domain.product.ProductType
import sample.cafekiosk.spring.domain.stock.Stock
import sample.cafekiosk.spring.domain.stock.StockRepository
import java.time.LocalDateTime

@ActiveProfiles("test")
@SpringBootTest
class OrderServiceTest(
    @Autowired
    private val orderService: OrderService,
    @Autowired
    private val orderRepository: OrderRepository,
    @Autowired
    private val orderProductRepository: OrderProductRepository,
    @Autowired
    private val productRepository: ProductRepository,
    @Autowired
    private val stockRepository: StockRepository,
) {

    @AfterEach
    fun tearDown() {
        orderProductRepository.deleteAllInBatch()
        orderRepository.deleteAllInBatch()
        productRepository.deleteAllInBatch()
        stockRepository.deleteAllInBatch()
    }

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

    @Test
    fun `중복되는 상품번호 리스트로 주문을 생성할 수 있다`() {
        // given
        val product1 = createProduct(ProductType.HANDMADE, "001", 1000)
        val product2 = createProduct(ProductType.HANDMADE, "002", 3000)
        val product3 = createProduct(ProductType.HANDMADE, "003", 5000)
        productRepository.saveAll(listOf(product1, product2, product3))
        val req =
            OrderCreateRequest(
                productNumbers = listOf("001", "001"),
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
            .contains(registeredDateTime, 2000)
        assertThat(res.orderProducts)
            .hasSize(2)
            .extracting("productNumber", "price")
            .containsExactlyInAnyOrder(
                tuple("001", 1000),
                tuple("001", 1000),
            )

    }

    @Test
    fun `재고와 관련된 상품이 포함되어 있는 주문번호 리스트를 받아 주문을 생성한다`() {
        // given
        val product1 = createProduct(ProductType.BOTTLE, "001", 1000)
        val product2 = createProduct(ProductType.BAKERY, "002", 3000)
        val product3 = createProduct(ProductType.HANDMADE, "003", 5000)
        productRepository.saveAll(listOf(product1, product2, product3))

        val stock1 = Stock.of("001", 2)
        val stock2 = Stock.of("002", 2)
        stockRepository.saveAll(listOf(stock1, stock2))

        val req =
            OrderCreateRequest(
                productNumbers = listOf("001", "001", "002", "003"),
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
            .contains(registeredDateTime, 10000)
        assertThat(res.orderProducts)
            .hasSize(4)
            .extracting("productNumber", "price")
            .containsExactlyInAnyOrder(
                tuple("001", 1000),
                tuple("001", 1000),
                tuple("002", 3000),
                tuple("003", 5000),
            )
        val stocks = stockRepository.findAll()
        assertThat(stocks)
            .extracting("productNumber", "quantity")
            .containsExactlyInAnyOrder(
                tuple("001", 0),
                tuple("002", 1),
            )
    }


    @Test
    fun `재고가 부족한 상품으로 주문을 생성하려는 경우 예외가 발생`() {
        // given
        val product1 = createProduct(ProductType.BOTTLE, "001", 1000)
        val product2 = createProduct(ProductType.BAKERY, "002", 3000)
        val product3 = createProduct(ProductType.HANDMADE, "003", 5000)
        productRepository.saveAll(listOf(product1, product2, product3))

        val stock1 = Stock.of("001", 2)
        val stock2 = Stock.of("002", 2)
        stock1.deductQuantity(1) // TODO
        stockRepository.saveAll(listOf(stock1, stock2))

        val req =
            OrderCreateRequest(
                productNumbers = listOf("001", "001", "002", "003"),
            )
        val registeredDateTime = LocalDateTime.now()
        // when & then
        assertThatThrownBy { orderService.createOrder(req, registeredDateTime) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("재고가 부족한 상품이 있습니다.")
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
