package io.github.jongminchung.springbatchexample.domain.command;

import lombok.Builder;

import java.math.BigDecimal;

public record PgSalesAmountMaterial(
        BigDecimal sellPrice, // 판매가
        BigDecimal promotionAmount, // 프로모션 금액 빼기
        BigDecimal mileageUsageAmount // 적립금 사용 금액 빼기
) {
    @Builder(toBuilder = true)
    public PgSalesAmountMaterial {
        /// 금액의 검증
    }
}
