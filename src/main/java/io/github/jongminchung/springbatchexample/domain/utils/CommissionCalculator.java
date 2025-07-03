package io.github.jongminchung.springbatchexample.domain.utils;

import io.github.jongminchung.springbatchexample.domain.entity.Seller;
import io.github.jongminchung.springbatchexample.domain.entity.order.OrderItemSnapshot;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RequiredArgsConstructor
public class CommissionCalculator {

    private final OrderItemSnapshot orderItemSnapshot;

    public BigDecimal getCommissionAmount() {
        val seller = orderItemSnapshot.getSeller();

        // 마진 금액 (판매가 - 공급가)
        val marginAmount = orderItemSnapshot.getSellPrice()
                .subtract(orderItemSnapshot.getSupplyPrice());

        // 수수료 비율 (int -> %)
        val rate = BigDecimal.valueOf(seller.getCommission())
                .divide(BigDecimal.valueOf(100), RoundingMode.CEILING);

        return marginAmount.multiply(rate);
    }
}
