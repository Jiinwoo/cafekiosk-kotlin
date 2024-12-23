package sample.cafekiosk.spring.domain.product

enum class ProductSellingStatus(
    val description: String,
) {
    SELLING("판매중"),
    HOLD("판매 보류"),
    STOP_SELLING("판매 중지"), ;

    companion object {
        fun forDisplay(): List<ProductSellingStatus> = listOf(SELLING, HOLD)
    }
}
