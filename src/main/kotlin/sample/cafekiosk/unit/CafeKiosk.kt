package sample.cafekiosk.unit

import sample.cafekiosk.unit.beverage.Beverage
import sample.cafekiosk.unit.order.Order
import java.time.LocalDateTime
import java.time.LocalTime

val SHOP_OPEN_TIME: LocalTime = LocalTime.of(10, 0)
val SHOP_CLOSE_TIME: LocalTime = LocalTime.of(22, 0)

class CafeKiosk(
    private val beverages: MutableList<Beverage> = mutableListOf(),
) {
    fun getBeverages(): List<Beverage> = beverages

    fun add(beverage: Beverage) {
        beverages.add(beverage)
    }

    fun add(
        beverage: Beverage,
        count: Int,
    ) {
        if (count < 1) {
            throw IllegalArgumentException("음료는 1개 이상 주문해야 합니다.")
        }

        repeat(count) {
            beverages.add(beverage)
        }
    }

    fun calculateTotalPrice(): Int = beverages.sumOf { it.getPrice() }

    fun remove(beverage: Beverage) {
        beverages.remove(beverage)
    }

    fun clearAll() {
        beverages.clear()
    }

    fun createOrder(): Order {
        val currentDateTime = java.time.LocalDateTime.now()
        val currentTime = currentDateTime.toLocalTime()

        if (currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME)) {
            throw IllegalArgumentException("주문은 영업시간에만 가능합니다.")
        }

        return Order(beverages = beverages, orderDateTime = currentDateTime)
    }

    fun createOrder(currentDateTime: LocalDateTime): Order {
//        val currentDateTime = java.time.LocalDateTime.now()
        val currentTime = currentDateTime.toLocalTime()

        if (currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME)) {
            throw IllegalArgumentException("주문은 영업시간에만 가능합니다.")
        }

        return Order(beverages = beverages, orderDateTime = currentDateTime)
    }
}
