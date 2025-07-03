package io.github.jongminchung.springbatchexample.domain.projection;

import io.github.jongminchung.springbatchexample.domain.enums.SellType;
import io.github.jongminchung.springbatchexample.domain.enums.TaxType;
import org.jspecify.annotations.Nullable;

import java.math.BigDecimal;

public record SummingSettlementResponse(
        Long sellerId,
        String sellerName,
        @Nullable String sellerBusinessNo,
        TaxType taxType,
        SellType sellType,
        BigDecimal sumPgSalesAmount,
        BigDecimal sumCouponDiscountAmount,
        BigDecimal sumMileageUsageAmount,
        BigDecimal sumShippingFeeAmount,
        BigDecimal sumClaimShippingFeeAmount,
        BigDecimal sumCommissionAmount,
        BigDecimal sumTaxAmount
) {
}
