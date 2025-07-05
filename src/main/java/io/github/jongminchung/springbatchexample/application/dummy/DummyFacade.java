package io.github.jongminchung.springbatchexample.application.dummy;

import io.github.jongminchung.springbatchexample.domain.entity.Product;
import io.github.jongminchung.springbatchexample.domain.entity.Seller;
import io.github.jongminchung.springbatchexample.domain.entity.order.OrderItem;
import io.github.jongminchung.springbatchexample.domain.entity.order.OrderItemSnapshot;
import io.github.jongminchung.springbatchexample.domain.enums.SellType;
import io.github.jongminchung.springbatchexample.infrastructure.database.repository.ClaimItemRepository;
import io.github.jongminchung.springbatchexample.infrastructure.database.repository.ClaimReceiptRepository;
import io.github.jongminchung.springbatchexample.infrastructure.database.repository.OrderItemRepository;
import io.github.jongminchung.springbatchexample.infrastructure.database.repository.OrderItemSnapshotRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DummyFacade {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemSnapshotRepository orderItemSnapshotRepository;
    private final ClaimReceiptRepository claimReceiptRepository;
    private final ClaimItemRepository claimItemRepository;

    public void createOrder(Long orderId) {
        val orderItemSnapshot = OrderItemSnapshot.builder()
                .productId(1L)
                .sellerId(1L)
                .sellPrice(BigDecimal.valueOf(10_000.000))
                .supplyPrice(BigDecimal.valueOf(1_000.000))
                .promotionAmount(BigDecimal.valueOf(2_000.000))
                .mileageUsageAmount(BigDecimal.valueOf(2_000.000))
                .build();
        orderItemSnapshotRepository.save(orderItemSnapshot);

        val orderItem = OrderItem.builder()
                .orderId(orderId)
                .orderCount(1)
                .itemDeliveryStatus(0)
                .shipCompleteAt(LocalDateTime.now())
                .orderItemSnapshot(orderItemSnapshot)
                .build();
        orderItemRepository.save(orderItem);
    }
}
