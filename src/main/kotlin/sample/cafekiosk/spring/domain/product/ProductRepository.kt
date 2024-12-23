package sample.cafekiosk.spring.domain.product

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
    fun findAllByProductNumberIn(productNumbers: List<String>): MutableList<Product>

    fun findAllBySellingStatusIn(sellingStatus: List<ProductSellingStatus>): MutableList<Product>
}
