package io.github.jongminchung.springbatchexample.domain;

import java.math.BigDecimal;

public sealed interface TaxTypeItem {

    record TaxItem(BigDecimal price) implements TaxTypeItem {
    }

    record ZeroTaxItem(BigDecimal price) implements TaxTypeItem {
    }

    record TaxFreeItem(BigDecimal price) implements TaxTypeItem {
    }
}
