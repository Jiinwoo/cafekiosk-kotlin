package sample.cafekiosk.spring.api.controller.order

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest
import sample.cafekiosk.spring.api.service.order.OrderService
import java.time.LocalDateTime

@RestController
class OrderController(
    private val orderService: OrderService,
) {
    @PostMapping("/api/v1/orders/new")
    fun createOrder(
        @RequestBody req: OrderCreateRequest,
    ) {
        val registeredDateTime = LocalDateTime.now()

        orderService.createOrder(
            req,
            registeredDateTime,
        )
    }
}
