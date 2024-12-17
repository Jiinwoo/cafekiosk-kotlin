package sample.cafekiosk.unit

import sample.cafekiosk.unit.beverage.Americano
import sample.cafekiosk.unit.beverage.Latte


fun main(args: Array<String>) {
    val cafeKiosk = CafeKiosk()
    cafeKiosk.add(Americano())
    println("아메리카노 추가")
    cafeKiosk.add(Latte())
    println("라떼 추가")
    println("총 가격: ${cafeKiosk.calculateTotalPrice()}원")
}