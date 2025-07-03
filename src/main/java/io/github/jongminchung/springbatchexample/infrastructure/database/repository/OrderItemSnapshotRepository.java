package io.github.jongminchung.springbatchexample.infrastructure.database.repository;

import io.github.jongminchung.springbatchexample.domain.entity.order.OrderItemSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemSnapshotRepository extends JpaRepository<OrderItemSnapshot, Long> {
}
