package sample.cafekiosk.spring.domain.stock

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class StockRepositoryTest(
    @Autowired
    private val stockRepository: StockRepository
){
    @Test
    fun `상품번호 리스트로 재고를 조회한다`() {
        //given
        val stock1 = Stock.of("001", 1)
        val stock2 = Stock.of("002", 2)
        val stock3 = Stock.of("003", 3)
        stockRepository.saveAll(listOf(stock1, stock2, stock3))
        //when
        val stocks = stockRepository.findAllByProductNumberIn(listOf("001", "002", "003"))
        //then
        assertThat(stocks)
            .hasSize(3)
            .extracting("productNumber", "quantity")
            .containsExactlyInAnyOrder(
                tuple("001", 1),
                tuple("002", 2),
                tuple("003", 3)
            )
    }

}
