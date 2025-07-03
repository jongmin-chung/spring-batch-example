package io.github.jongminchung.springbatchexample.core.job.total;

import io.github.jongminchung.springbatchexample.domain.projection.SummingSettlementResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.jspecify.annotations.NonNull;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.database.orm.AbstractJpaQueryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.util.Pair;

import java.time.LocalDate;

@Configuration
public class TotalSettlementItemReaderConfig {

    private static final int CHUNK_SIZE = 500;

    @Bean
    public JpaPagingItemReader<SummingSettlementResponse> totalSettlementJpaItemReader(EntityManager entityManager) {
        return new JpaPagingItemReaderBuilder<SummingSettlementResponse>()
                .name("totalSettlementJpaItemReader")
                .entityManagerFactory(entityManager.getEntityManagerFactory())
                .pageSize(CHUNK_SIZE)
                .queryProvider(new SummingSettlementDailyQueryProvider(datePair()))
                .build();
    }

    @RequiredArgsConstructor
    public static class SummingSettlementDailyQueryProvider extends AbstractJpaQueryProvider {
        private final Pair<LocalDate, LocalDate> datePair;

        @Override
        public @NonNull Query createQuery() {
            return this.getEntityManager().createQuery(
                            """
                                    select sd.sellerId,
                                        sd.sellerName,
                                        sd.sellerBusinessNo,
                                        sd.taxType,
                                        sd.sellType,
                                        sum(sd.pgSalesAmount) as sumPgSalesAmount,
                                        sum(sd.couponDiscountAmount) as sumCouponDiscountAmount,
                                        sum(sd.mileageUsageAmount) as sumMileageUsageAmount,
                                        sum(sd.shippingFeeAmount) as sumShippingFeeAmount,
                                        sum(sd.claimShippingFeeAmount) as sumClaimShippingFeeAmount,
                                        sum(sd.commissionAmount) as sumCommissionAmount,
                                        sum(sd.taxAmount) as sumTaxAmount
                                    from SettlementDaily sd
                                    where sd.settlementDate between :startDate and :endDate
                                    group by sellerId
                                    """
                            , SummingSettlementResponse.class)
                    .setParameter("startDate", datePair.getFirst())
                    .setParameter("endDate", datePair.getSecond());
        }

        @Override
        public void afterPropertiesSet() {
            throw new UnsupportedOperationException("TODO");
        }
    }

    private Pair<LocalDate, LocalDate> datePair() {
        val startDate = LocalDate.now().withDayOfMonth(1);

        val lastStartDate = startDate.minusMonths(1);
        val lastEndDate = lastStartDate.withDayOfMonth(lastStartDate.lengthOfMonth());

        return Pair.of(lastStartDate, lastEndDate);
    }
}
