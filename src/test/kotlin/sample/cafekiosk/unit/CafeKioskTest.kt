package sample.cafekiosk.unit

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import sample.cafekiosk.unit.beverage.Americano
import sample.cafekiosk.unit.beverage.Latte
import java.time.LocalDateTime

@Suppress("NonAsciiCharacters")
class CafeKioskTest {
//    @DisplayName("음료를 1개 추가하면 주문 목록에 담긴다.")
    @Test
    fun `음료를 1개 추가하면 주문 목록에 담긴다`() {
        val cafeKiosk = CafeKiosk()
        cafeKiosk.add(Latte())
        cafeKiosk.add(Americano())

        assertEquals(2, cafeKiosk.getBeverages().size)
        assertThat(cafeKiosk.getBeverages()).hasSize(2)
        assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("라떼")
    }

    @Test
    fun `addSeveralBeverages`() {
        val cafeKiosk = CafeKiosk()
        val latte = Latte()
        cafeKiosk.add(latte, 2)
        assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(latte)
        assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(latte)
    }

    @Test
    fun `addZeroBeverages`() {
        val cafeKiosk = CafeKiosk()
        val latte = Latte()
        assertThatThrownBy {
            cafeKiosk.add(latte, 0)
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("음료는 1개 이상 주문해야 합니다.")
    }

    @Test
    fun remove() {
        val cafeKiosk = CafeKiosk()
        val beverage = Americano()
        cafeKiosk.add(beverage)
        assertThat(cafeKiosk.getBeverages()).hasSize(1)

        cafeKiosk.remove(beverage)
        assertThat(cafeKiosk.getBeverages()).isEmpty()
    }

    @Test
    fun `테스트하기 해보기`() {
        // given
        // when
        // then
    }

    @Test
    fun clear() {
        val cafeKiosk = CafeKiosk()
        cafeKiosk.add(Americano())
        cafeKiosk.add(Latte())
        assertThat(cafeKiosk.getBeverages()).hasSize(2)

        cafeKiosk.clearAll()
        assertThat(cafeKiosk.getBeverages()).isEmpty()
    }

    @Test
    fun calculateTotalPrice() {
        // given
        val cafeKiosk = CafeKiosk()
        val americano = Americano()
        val latte = Latte()

        cafeKiosk.add(americano)
        cafeKiosk.add(latte)
        // when
        val totalPrice = cafeKiosk.calculateTotalPrice()
        // then
        assertThat(totalPrice).isEqualTo(8500)
    }

    @Test
    fun createOrder() {
        val cafeKiosk = CafeKiosk()
        cafeKiosk.add(Americano())

        val order = cafeKiosk.createOrder()
        assertThat(order.getBeverages()).hasSize(1)
        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노")
    }

    @Test
    fun createOrderTime() {
        val cafeKiosk = CafeKiosk()
        cafeKiosk.add(Americano())

        val order = cafeKiosk.createOrder(LocalDateTime.of(2023, 1, 17, 10, 0))
        assertThat(order.getBeverages()).hasSize(1)
        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노")
    }

    @DisplayName("영업 시작 시간 이전에는 주문을 생성할 수 없다.")
    @Test
    fun createOrderOutsideOpenTime() {
        val cafeKiosk = CafeKiosk()
        cafeKiosk.add(Americano())
        assertThatThrownBy {
            cafeKiosk.createOrder(LocalDateTime.of(2023, 1, 17, 9, 59, 59))
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("주문은 영업시간에만 가능합니다.")
    }
}
