package io.github.jongminchung.springbatchexample.core.job.purchaseconfirmed.delivery;

import io.github.jongminchung.springbatchexample.domain.entity.order.OrderItem;
import io.github.jongminchung.springbatchexample.infrastructure.database.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PurchasedConfirmWriter implements ItemWriter<OrderItem> {

    private final OrderItemRepository orderItemRepository;

    @Override
    public void write(Chunk<? extends OrderItem> chunk) {
        for (val orderItem : chunk) {
            val newItem = orderItem.toBuilder()
                    .id(orderItem.getId())
                    .purchaseConfirmAt(LocalDateTime.now())
                    .build();

            orderItemRepository.save(newItem);
        }
    }
}
