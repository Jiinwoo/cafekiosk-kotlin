package sample.cafekiosk.spring.domain.order

enum class OrderStatus(
    val description: String,
) {
    INIT("주문 생성"),
    CANCELED("주문 취소"),
    PAYMENT_COMPLETED("결제 완료"),
    PAYMENT_FAILED("결제 실패"),
    RECEIVED("주문 접수"),
    COMPLETED("처리 완료"),
}
