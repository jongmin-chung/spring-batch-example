package io.github.jongminchung.springbatchexample.core.job.total;

import io.github.jongminchung.springbatchexample.domain.entity.settlement.SettlementTotal;
import io.github.jongminchung.springbatchexample.domain.projection.SummingSettlementResponse;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TotalSettlementItemProcessor implements ItemProcessor<SummingSettlementResponse, SettlementTotal> {

    @Override
    public SettlementTotal process(SummingSettlementResponse item) {
        return SettlementTotal.builder()
                .settlementDate(LocalDate.now())
                .sellerId(item.sellerId())
                .sellerName(item.sellerName())
                .sellerBusinessNo(item.sellerBusinessNo())
                .taxType(item.taxType())
                .sellType(item.sellType())
                .pgSalesAmount(item.sumPgSalesAmount())
                .couponDiscountAmount(item.sumCouponDiscountAmount())
                .mileageUsageAmount(item.sumMileageUsageAmount())
                .shippingFeeAmount(item.sumShippingFeeAmount())
                .claimShippingFeeAmount(item.sumClaimShippingFeeAmount())
                .commissionAmount(item.sumCommissionAmount())
                .taxAmount(item.sumTaxAmount())
                .build();
    }
}
