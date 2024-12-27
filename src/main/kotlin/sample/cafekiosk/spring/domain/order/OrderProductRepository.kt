package sample.cafekiosk.spring.domain.order

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderProductRepository : JpaRepository<OrderProduct, Long> {
}
