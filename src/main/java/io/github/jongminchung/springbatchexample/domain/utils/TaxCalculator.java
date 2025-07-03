package io.github.jongminchung.springbatchexample.domain.utils;

import io.github.jongminchung.springbatchexample.domain.TaxTypeItem;
import io.github.jongminchung.springbatchexample.domain.entity.order.OrderItemSnapshot;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class TaxCalculator {

    private static final double TAX_RATE = 0.03; // 3%

    private final OrderItemSnapshot orderItemSnapshot;

    public TaxTypeItem getTaxTypeItem() {
        return switch (orderItemSnapshot.getTaxType()) {
            case TAX -> new TaxTypeItem.TaxItem(orderItemSnapshot.getSellPrice());
            case FREE -> new TaxTypeItem.TaxFreeItem(orderItemSnapshot.getSellPrice());
            case ZERO -> new TaxTypeItem.ZeroTaxItem(orderItemSnapshot.getSellPrice());
        };
    }

    public BigDecimal getTaxAmount() {
        val taxTypeItem = getTaxTypeItem();

        return switch (taxTypeItem) {
            case TaxTypeItem.TaxItem taxItem -> taxItem.price().multiply(BigDecimal.valueOf(TAX_RATE));
            case TaxTypeItem.ZeroTaxItem zeroTaxItem -> zeroTaxItem.price();
            case TaxTypeItem.TaxFreeItem taxFreeItem -> taxFreeItem.price();
        };
    }
}
