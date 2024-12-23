package sample.cafekiosk.spring.api.controller.order.request

data class OrderCreateRequest(
    val productNumbers: List<String>,
)
