package sample.cafekiosk.unit.beverage

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class AmericanoTest {

    @Test
    fun getName() {
        val americano = Americano()
        assertEquals("아메리카노", americano.getName())
        assertThat(americano.getName()).isEqualTo("아메리카노")
    }

    @Test
    fun getPrice() {
        val americano = Americano()
        assertEquals(4000, americano.getPrice())
        assertThat(americano.getPrice()).isEqualTo(4000)
    }
}