package io.github.jongminchung.springbatchexample.domain.utils;

import io.github.jongminchung.springbatchexample.domain.command.PgSalesAmountMaterial;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class PgSalesAmountCalculatorTest {

    PgSalesAmountMaterial samplePgSalesAmountMaterial = PgSalesAmountMaterial.builder()
            .sellPrice(BigDecimal.valueOf(10_000.000))
            .promotionAmount(BigDecimal.valueOf(0.000))
            .mileageUsageAmount(BigDecimal.valueOf(0.000))
            .build();

    @DisplayName("프로모션도 없고, 적립금 사용도 없는 경우")
    @Test
    void test1() {
        val calculator = new PgSalesAmountCalculator(samplePgSalesAmountMaterial);
        val pgSalesAmount = calculator.getPgSalesAmount();

        assertThat(pgSalesAmount).isEqualTo(BigDecimal.valueOf(10_000.0));
    }

    @DisplayName("프로모션이 일부 발생 (1,000원), 적립금 사용 없는 경우")
    @Test
    void test2() {
        val pgSalesAmountMaterial = samplePgSalesAmountMaterial.toBuilder()
                .promotionAmount(BigDecimal.valueOf(1_000.0))
                .build();
        val calculator = new PgSalesAmountCalculator(pgSalesAmountMaterial);
        val pgSalesAmount = calculator.getPgSalesAmount();

        assertThat(pgSalesAmount).isEqualTo(BigDecimal.valueOf(9_000.0));
    }

    @DisplayName("프로모션이 일부 발생 (1,000원), 적립금 사용 나머지인 경우")
    @Test
    void test3() {
        val pgSalesAmountMaterial = samplePgSalesAmountMaterial.toBuilder()
                .promotionAmount(BigDecimal.valueOf(1_000.0))
                .mileageUsageAmount(BigDecimal.valueOf(9_000.000))
                .build();
        val calculator = new PgSalesAmountCalculator(pgSalesAmountMaterial);
        val pgSalesAmount = calculator.getPgSalesAmount();

        assertThat(pgSalesAmount).usingComparator(BigDecimal::compareTo)
                .isEqualTo(BigDecimal.ZERO);
    }
}
