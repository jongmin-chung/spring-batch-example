package io.github.jongminchung.springbatchexample.core.job.purchaseconfirmed.settlement;

import io.github.jongminchung.springbatchexample.domain.entity.order.OrderItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.database.orm.AbstractJpaQueryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.util.Pair;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Configuration
public class SettlementDailyItemReaderConfig {

    private static final int CHUNK_SIZE = 500;

    @Bean
    public JpaPagingItemReader<OrderItem> dailySettlementJpaItemReader(EntityManager entityManager) {
        return new JpaPagingItemReaderBuilder<OrderItem>()
                .name("dailySettlementJpaItemReader")
                .entityManagerFactory(entityManager.getEntityManagerFactory())
                .pageSize(CHUNK_SIZE)
                .queryProvider(new PurchaseConfirmJpaQueryProvider(dateTimePair()))
                .build();
    }

    @RequiredArgsConstructor
    public static class PurchaseConfirmJpaQueryProvider extends AbstractJpaQueryProvider {
        private final Pair<LocalDateTime, LocalDateTime> dateTimePair;

        @Override
        public Query createQuery() {
            return this.getEntityManager().createQuery(
                            """
                                    SELECT oi
                                    FROM OrderItem oi
                                    WHERE purchaseConfirmAt BETWEEN :startDateTime AND :endDateTime
                                    """
                            , OrderItem.class)
                    .setParameter("startDateTime", dateTimePair.getFirst())
                    .setParameter("endDateTime", dateTimePair.getSecond());
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            throw new UnsupportedOperationException("TODO");
        }
    }

    private static Pair<LocalDateTime, LocalDateTime> dateTimePair() {
        val startDateTime = LocalDateTime.of(
                LocalDate.now(),
                LocalTime.MIN
        );

        val endDateTime = LocalDateTime.of(
                LocalDate.now(),
                LocalTime.MAX
        );

        return Pair.of(startDateTime, endDateTime);
    }
}
