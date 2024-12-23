package sample.cafekiosk.unit.order

import sample.cafekiosk.unit.beverage.Beverage
import java.time.LocalDateTime

class Order(
    private val orderDateTime: LocalDateTime,
    private val beverages: MutableList<Beverage>
) {
    fun getBeverages(): List<Beverage> {
        return beverages
    }

}