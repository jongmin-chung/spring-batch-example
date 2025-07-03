package io.github.jongminchung.springbatchexample.domain.entity.order;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private long id = 0L;

    private long orderId;
    private int orderCount;

    /// TODO: Enum으로 변경
    private int itemDeliveryStatus;

    @Comment("구매 확정일시")
    private LocalDateTime purchaseConfirmAt = null;
    private LocalDateTime shipCompleteAt = null;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_snapshot_id", insertable = false, updatable = false)
    private OrderItemSnapshot orderItemSnapshot;

    @Column(name = "order_item_snapshot_id", insertable = false, updatable = false)
    private long orderItemSnapshotId;
}
