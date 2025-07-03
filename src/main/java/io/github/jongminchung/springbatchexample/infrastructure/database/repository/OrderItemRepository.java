package io.github.jongminchung.springbatchexample.infrastructure.database.repository;

import io.github.jongminchung.springbatchexample.domain.entity.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
