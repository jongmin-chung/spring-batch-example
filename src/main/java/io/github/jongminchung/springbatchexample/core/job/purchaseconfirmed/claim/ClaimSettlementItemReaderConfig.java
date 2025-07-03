package io.github.jongminchung.springbatchexample.core.job.purchaseconfirmed.claim;

import io.github.jongminchung.springbatchexample.domain.entity.claim.ClaimItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.database.orm.AbstractJpaQueryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClaimSettlementItemReaderConfig {

    private static final int CHUNK_SIZE = 500;

    @Bean
    public JpaPagingItemReader<ClaimItem> claimSettlementJpaItemReader(EntityManager entityManager) {
        return new JpaPagingItemReaderBuilder<ClaimItem>()
                .name("claimSettlementJpaItemReader")
                .entityManagerFactory(entityManager.getEntityManagerFactory())
                .pageSize(CHUNK_SIZE)
                .queryProvider(new ClaimItemJpaQueryProvider())
                .build();
    }

    @RequiredArgsConstructor
    public static class ClaimItemJpaQueryProvider extends AbstractJpaQueryProvider {
        @Override
        public Query createQuery() {
            /// TODO: sd.id IS NULL 인가?
            return this.getEntityManager().createQuery(
                    """
                            SELECT ci
                            FROM ClaimItem ci
                            LEFT OUTER JOIN SettlementDaily sd ON sd.claimReceiptId = ci.id
                            JOIN ClaimReceipt cr ON cr.id = ci.claimReceiptId
                            WHERE cr.completeAt IS NOT NULL AND sd.id IS NULL
                            """
                    , ClaimItem.class);
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            throw new UnsupportedOperationException("TODO");
        }
    }
}
