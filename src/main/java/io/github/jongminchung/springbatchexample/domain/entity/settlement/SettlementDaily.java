package io.github.jongminchung.springbatchexample.domain.entity.settlement;

import io.github.jongminchung.springbatchexample.domain.enums.SellType;
import io.github.jongminchung.springbatchexample.domain.enums.TaxType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


/// 정산 엔티티는 private final 필드로 선언한다.
@Getter
@Entity
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
public class SettlementDaily {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "settlement_daily_id")
    private final long id;

    @Column(name = "settlement_date")
    private final LocalDate settlementDate;

    @Column(name = "order_id")
    private final long orderId;

    @Column(name = "order_item_id")
    private final long orderItemId;

    @Column(name = "order_count")
    private final int orderCount;

    @Column(name = "claim_receipt_id")
    private final Long claimReceiptId;

    private final long sellerId;
    private final String sellerName;
    private final int sellerBusinessNo;

    private final TaxType taxType;
    private final SellType sellType;

    @Column(precision = 16, scale = 5)
    private final BigDecimal pgSalesAmount;
    @Column(precision = 16, scale = 5)
    private final BigDecimal couponDiscountAmount;
    @Column(precision = 16, scale = 5)
    private final BigDecimal mileageUsageAmount;
    @Column(precision = 16, scale = 5)
    private final BigDecimal shippingFeeAmount;
    @Column(precision = 16, scale = 5)
    private final BigDecimal claimShippingFeeAmount;
    @Column(precision = 16, scale = 5)
    private final BigDecimal commissionAmount;
    @Column(precision = 16, scale = 5)
    private final BigDecimal taxAmount;
}
