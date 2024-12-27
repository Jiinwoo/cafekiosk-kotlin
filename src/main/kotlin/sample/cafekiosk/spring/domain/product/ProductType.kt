package sample.cafekiosk.spring.domain.product

enum class ProductType(
    val description: String,
) {
    HANDMADE("제조 음료"),
    BOTTLE("병음료"),
    BAKERY("베이커리"), ;

    companion object {
        fun containsStockType(productType: ProductType): Boolean {
            return productType == BOTTLE || productType == BAKERY
        }
    }
}
