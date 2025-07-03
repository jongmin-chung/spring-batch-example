package io.github.jongminchung.springbatchexample.domain.entity.claim;

import io.github.jongminchung.springbatchexample.domain.entity.order.OrderItem;
import io.github.jongminchung.springbatchexample.domain.enums.ExtraFeePayer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClaimItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "claim_id")
    private Long id;

    private int claimCount = 0;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id", insertable = false, updatable = false)
    private OrderItem orderItem;

    @ManyToOne
    @JoinColumn(name = "claim_receipt_id", insertable = false, updatable = false)
    private ClaimReceipt claimReceipt;

    private long claimReceiptId;

    public int getShippingCount() {
        if (this.claimReceipt.getExtraFeePayer() == ExtraFeePayer.BUYER) {
            return 0;
        }

        return switch (this.claimReceipt.getClaimType()) {
            case CANCEL -> 0; // 취소
            case EXCHANGE -> -2; // 교환
            case RETURN -> -1; // 반품
        };
    }
}
