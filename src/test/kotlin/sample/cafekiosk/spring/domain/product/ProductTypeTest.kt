package sample.cafekiosk.spring.domain.product

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ProductTypeTest {
    @Test
    fun `상품 타입이 재고 관련 타입인지를 체크한다`() {
        //given
        val given = ProductType.BAKERY
        //when
        val result = ProductType.containsStockType(given)
        //then
        assertThat(result).isTrue()
    }

}
