package io.github.jongminchung.springbatchexample.domain.utils;

import io.github.jongminchung.springbatchexample.domain.entity.claim.ClaimItem;
import io.github.jongminchung.springbatchexample.domain.entity.order.OrderItem;
import io.github.jongminchung.springbatchexample.domain.entity.settlement.SettlementDaily;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
public class NegativeDailySettlementCollection {

    private final ClaimItem item;

    public SettlementDaily getSettlementDaily() {
        val orderItem = item.getOrderItem();
        val orderItemSnapshot = orderItem.getOrderItemSnapshot();

        val count = BigDecimal.valueOf(item.getClaimCount());
        val seller = orderItemSnapshot.getSeller();

        // 세금 계산
        val taxCalculator = new TaxCalculator(orderItemSnapshot);
        val taxAmount = taxCalculator.getTaxAmount().multiply(count);

        // - 정산 금액에 필요한 데이터 만들기
        val pgCalculator = new PgSalesAmountCalculator(orderItemSnapshot);
        val pgSalesAmount = pgCalculator.getPgSalesAmount().multiply(count);

        // 수수료 금액 계산
        val commissionCalculator = new CommissionCalculator(orderItemSnapshot);
        val commissionAmount = commissionCalculator.getCommissionAmount().multiply(count);

        val claimShippingFeeAmount = new ClaimShippedAmountCalculator(item)
                .getClaimShippedAmount();

        return SettlementDaily.builder()
                .settlementDate(LocalDate.now())
                .orderId(orderItem.getOrderId())
                .orderCount(item.getClaimCount())
                .orderItemId(orderItem.getId())
                .sellerId(seller.getId())
                .sellerName(seller.getName())
                .sellerBusinessNo(seller.getBusinessNo())
                .taxType(orderItemSnapshot.getTaxType())
                .taxAmount(taxAmount)
                .commissionAmount(commissionAmount)
                .pgSalesAmount(pgSalesAmount)
                .couponDiscountAmount(orderItemSnapshot.getPromotionAmount())
                .mileageUsageAmount(orderItemSnapshot.getMileageUsageAmount())
                .shippingFeeAmount(orderItemSnapshot.getDefaultDeliveryAmount())
                .claimReceiptId(item.getClaimReceiptId())
                .claimShippingFeeAmount(claimShippingFeeAmount)
                .build();
    }

}
