package io.github.jongminchung.springbatchexample.domain.utils;

import io.github.jongminchung.springbatchexample.domain.command.PgSalesAmountMaterial;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class PgSalesAmountCalculator {

    private final PgSalesAmountMaterial pgSalesAmountMaterial;

    public BigDecimal getPgSalesAmount() {
        return pgSalesAmountMaterial.sellPrice()
                .subtract(pgSalesAmountMaterial.promotionAmount())
                .subtract(pgSalesAmountMaterial.mileageUsageAmount());
    }
}
