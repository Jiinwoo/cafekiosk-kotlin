package sample.cafekiosk.spring.api.service.order

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest
import sample.cafekiosk.spring.api.service.order.response.OrderResponse
import sample.cafekiosk.spring.domain.order.Order
import sample.cafekiosk.spring.domain.order.OrderRepository
import sample.cafekiosk.spring.domain.product.Product
import sample.cafekiosk.spring.domain.product.ProductRepository
import sample.cafekiosk.spring.domain.product.ProductType
import sample.cafekiosk.spring.domain.stock.Stock
import sample.cafekiosk.spring.domain.stock.StockRepository
import java.time.LocalDateTime

@Transactional
@Service
class OrderService(
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository,
    private val stockRepository: StockRepository,
) {
    /**
     * 재고 감소 -> 동시성 고민
     * optimistic lock / pessimistic lock / ...
     */
    fun createOrder(
        req: OrderCreateRequest,
        registeredDateTime: LocalDateTime,
    ): OrderResponse {
        val duplicateProducts = findProductsBy(req.productNumbers)

        deductStockQuantities(duplicateProducts)

        val order =
            Order.create(
                duplicateProducts,
                registeredDateTime,
            )

        val savedOrder = orderRepository.save(order)

        return OrderResponse.of(savedOrder)
    }

    private fun deductStockQuantities(duplicateProducts: MutableList<Product>) {
        // 재고 차감 체크가 필요한 상품들 filter
        val stockProductNumbers = extractStockProductNumbers(duplicateProducts)
        // 재고 엔티티 조회
        val stockMap = createStockMapBy(stockProductNumbers)
        // 상품별 counting
        val productCountingMap = createCountingMapBy(stockProductNumbers)

        // 재고 차감 시도
        stockProductNumbers
            .toSet()
            .forEach { productNumber ->
                val stock = stockMap[productNumber]!!
                val quantity = productCountingMap[productNumber]!!.toInt()
                if (stock.isQuantityLessThan(quantity)) {
                    throw IllegalArgumentException("재고가 부족한 상품이 있습니다.")
                }
                stock.deductQuantity(quantity)
            }
    }

    private fun extractStockProductNumbers(duplicateProducts: MutableList<Product>) =
        duplicateProducts
            .filter { ProductType.containsStockType(it.productType) }
            .map { it.productNumber }

    private fun createStockMapBy(stockProductNumbers: List<String>): Map<String, Stock> {
        val stocks = stockRepository.findAllByProductNumberIn(stockProductNumbers)
        val stockMap = stocks.associateBy { it.productNumber }
        return stockMap
    }

    private fun createCountingMapBy(stockProductNumbers: List<String>) =
        stockProductNumbers.groupingBy { it }.eachCount()


    private fun findProductsBy(productNumbers: List<String>): MutableList<Product> {
        val products = productRepository.findAllByProductNumberIn(productNumbers)

        val productMap = products.associateBy { it.productNumber }

        val duplicateProducts = productNumbers.map {
            productMap[it]!!
        }.toMutableList()
        return duplicateProducts
    }
}
