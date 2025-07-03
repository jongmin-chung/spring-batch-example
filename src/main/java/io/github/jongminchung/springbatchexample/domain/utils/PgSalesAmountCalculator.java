package io.github.jongminchung.springbatchexample.domain.utils;

import io.github.jongminchung.springbatchexample.domain.entity.order.OrderItemSnapshot;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class PgSalesAmountCalculator {

    private final OrderItemSnapshot orderItemSnapshot;

    public BigDecimal getPgSalesAmount() {
        return orderItemSnapshot.getSellPrice()
                .subtract(orderItemSnapshot.getPromotionAmount())
                .subtract(orderItemSnapshot.getMileageUsageAmount());
    }
}
