package io.github.jongminchung.springbatchexample.domain.utils;

import io.github.jongminchung.springbatchexample.domain.entity.claim.ClaimItem;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class ClaimShippedAmountCalculator {

    private static final BigDecimal SHIPPING_DEFAULT_FEE_AMOUNT = BigDecimal.valueOf(3000);

    private final ClaimItem claimItem;

    public BigDecimal getClaimShippedAmount() {
        var shippingCount = claimItem.getShippingCount(); // 취소는 0, 교환 2, 반품 1
        return SHIPPING_DEFAULT_FEE_AMOUNT.multiply(BigDecimal.valueOf(shippingCount));
    }
}
