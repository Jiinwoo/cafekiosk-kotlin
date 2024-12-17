package sample.cafekiosk.unit

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import sample.cafekiosk.unit.beverage.Americano
import sample.cafekiosk.unit.beverage.Latte
import java.time.LocalDateTime

class CafeKioskTest {
    @Test
    fun `add`() {
        val cafeKiosk = CafeKiosk()
        cafeKiosk.add(Latte())
        cafeKiosk.add(Americano())

        assertEquals(2, cafeKiosk.getBeverages().size)
        assertThat(cafeKiosk.getBeverages()).hasSize(2)
        assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("라떼")
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
    fun clear() {
        val cafeKiosk = CafeKiosk()
        cafeKiosk.add(Americano())
        cafeKiosk.add(Latte())
        assertThat(cafeKiosk.getBeverages()).hasSize(2)

        cafeKiosk.clearAll()
        assertThat(cafeKiosk.getBeverages()).isEmpty()
    }

 }