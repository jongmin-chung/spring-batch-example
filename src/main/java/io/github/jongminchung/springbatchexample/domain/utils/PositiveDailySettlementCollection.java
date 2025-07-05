package io.github.jongminchung.springbatchexample.domain.utils;

import io.github.jongminchung.springbatchexample.domain.command.PgSalesAmountMaterial;
import io.github.jongminchung.springbatchexample.domain.entity.order.OrderItem;
import io.github.jongminchung.springbatchexample.domain.entity.settlement.SettlementDaily;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
public class PositiveDailySettlementCollection {

    private final OrderItem item;

    public SettlementDaily getSettlementDaily() {
        val orderItemSnapshot = item.getOrderItemSnapshot();
        val count = BigDecimal.valueOf(item.getOrderCount());
        val seller = orderItemSnapshot.getSeller();

        // 세금 계산
        val taxCalculator = new TaxCalculator(orderItemSnapshot);
        val taxAmount = taxCalculator.getTaxAmount().multiply(count);

        // + 정산 금액에 필요한 데이터 만들기
        val pgSalesAmountMaterial = PgSalesAmountMaterial.builder()
                .sellPrice(orderItemSnapshot.getSellPrice())
                .promotionAmount(orderItemSnapshot.getPromotionAmount())
                .mileageUsageAmount(orderItemSnapshot.getMileageUsageAmount())
                .build();
        val pgCalculator = new PgSalesAmountCalculator(pgSalesAmountMaterial);
        val pgSalesAmount = pgCalculator.getPgSalesAmount().multiply(count);

        // 수수료 금액 계산
        val commissionCalculator = new CommissionCalculator(orderItemSnapshot);
        val commissionAmount = commissionCalculator.getCommissionAmount().multiply(count);

        return SettlementDaily.builder()
                .settlementDate(LocalDate.now())
                .orderId(item.getOrderId())
                .orderCount(item.getOrderCount())
                .orderItemId(item.getOrderItemSnapshotId())
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
                .build();
    }

}
