package sample.cafekiosk.spring.domain.product

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@DataJpaTest
class ProductRepositoryTest(
    @Autowired
    private val productRepository: ProductRepository,
) {
    @Test
    fun `원하는 판매 상태를 가진 상품들을 조회한다`() {
        // given
        val product1 =
            Product(
                productNumber = "001",
                productType = ProductType.HANDMADE,
                sellingStatus = ProductSellingStatus.SELLING,
                name = "아메리카노",
                price = 4000,
            )
        val product2 =
            Product(
                productNumber = "002",
                productType = ProductType.HANDMADE,
                sellingStatus = ProductSellingStatus.HOLD,
                name = "카페라떼",
                price = 4500,
            )
        val product3 =
            Product(
                productNumber = "003",
                productType = ProductType.HANDMADE,
                sellingStatus = ProductSellingStatus.STOP_SELLING,
                name = "팥빙수",
                price = 7000,
            )
        productRepository.saveAll(listOf(product1, product2, product3))
        // when
        val products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay())
        // then

        assertThat(products)
            .hasSize(2)
            .extracting("productNumber", "name", "sellingStatus")
            .containsExactlyInAnyOrder(
                tuple("001", "아메리카노", ProductSellingStatus.SELLING),
                tuple("002", "카페라떼", ProductSellingStatus.HOLD),
            )
    }

    @Test
    fun `상품번호 리스트로 상품들을 조회한다`() {
        // given
        val product1 =
            Product(
                productNumber = "001",
                productType = ProductType.HANDMADE,
                sellingStatus = ProductSellingStatus.SELLING,
                name = "아메리카노",
                price = 4000,
            )
        val product2 =
            Product(
                productNumber = "002",
                productType = ProductType.HANDMADE,
                sellingStatus = ProductSellingStatus.HOLD,
                name = "카페라떼",
                price = 4500,
            )
        val product3 =
            Product(
                productNumber = "003",
                productType = ProductType.HANDMADE,
                sellingStatus = ProductSellingStatus.STOP_SELLING,
                name = "팥빙수",
                price = 7000,
            )
        productRepository.saveAll(listOf(product1, product2, product3))
        // when
        val products = productRepository.findAllByProductNumberIn(listOf("001", "002"))
        // then
        assertThat(products)
            .hasSize(2)
            .extracting("productNumber", "name", "sellingStatus")
            .containsExactlyInAnyOrder(
                tuple("001", "아메리카노", ProductSellingStatus.SELLING),
                tuple("002", "카페라떼", ProductSellingStatus.HOLD),
            )
    }
}
