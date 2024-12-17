package sample.cafekiosk.unit

import sample.cafekiosk.unit.beverage.Beverage
import sample.cafekiosk.unit.order.Order

class CafeKiosk(
    private val beverages: MutableList<Beverage> = mutableListOf()
) {

    fun getBeverages(): List<Beverage> {
        return beverages
    }

    fun add(beverage: Beverage) {
        beverages.add(beverage)
    }

    fun calculateTotalPrice(): Int {
        return beverages.sumOf { it.getPrice() }
    }

    fun remove(beverage: Beverage) {
        beverages.remove(beverage)
    }

    fun clearAll() {
        beverages.clear()
    }

    fun createOrder(): Order {
        return Order(beverages = beverages, orderDateTime = java.time.LocalDateTime.now())
    }
}