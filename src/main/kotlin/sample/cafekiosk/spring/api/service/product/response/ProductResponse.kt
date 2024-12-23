package sample.cafekiosk.spring.api.service.product.response

import sample.cafekiosk.spring.domain.product.Product
import sample.cafekiosk.spring.domain.product.ProductSellingStatus
import sample.cafekiosk.spring.domain.product.ProductType

class ProductResponse private constructor(
    val id: Long,
    val productNumber: String,
    val productType: ProductType,
    val sellingType: ProductSellingStatus,
    val name: String,
    val price: Int,
) {
    companion object {
        fun of(entity: Product) =
            ProductResponse(
                id = entity.id,
                productNumber = entity.productNumber,
                productType = entity.productType,
                sellingType = entity.sellingStatus,
                name = entity.name,
                price = entity.price,
            )
    }
}
