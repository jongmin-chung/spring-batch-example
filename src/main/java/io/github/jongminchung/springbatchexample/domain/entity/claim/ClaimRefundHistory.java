package io.github.jongminchung.springbatchexample.domain.entity.claim;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClaimRefundHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "claim_refund_history_id")
    private Long id;

    private LocalDateTime refundAt;

    private long sellerId;

    private BigDecimal refundCashAmount = BigDecimal.ZERO; // 현금성 -> Cash
    private BigDecimal couponSaleAmount = BigDecimal.ZERO;
    private BigDecimal refundMileageAmount = BigDecimal.ZERO;
    private BigDecimal subtractDeliveryAmount = BigDecimal.ZERO;
    private BigDecimal refundDeliveryAmount = BigDecimal.ZERO;
}
