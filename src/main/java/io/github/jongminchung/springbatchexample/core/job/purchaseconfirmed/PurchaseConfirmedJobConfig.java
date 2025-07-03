package io.github.jongminchung.springbatchexample.core.job.purchaseconfirmed;

import io.github.jongminchung.springbatchexample.core.job.purchaseconfirmed.claim.ClaimSettlementItemProcessor;
import io.github.jongminchung.springbatchexample.core.job.purchaseconfirmed.claim.ClaimSettlementItemWriter;
import io.github.jongminchung.springbatchexample.core.job.purchaseconfirmed.settlement.SettlementDailyItemProcessor;
import io.github.jongminchung.springbatchexample.core.job.purchaseconfirmed.settlement.SettlementDailyItemWriter;
import io.github.jongminchung.springbatchexample.domain.entity.claim.ClaimItem;
import io.github.jongminchung.springbatchexample.domain.entity.order.OrderItem;
import io.github.jongminchung.springbatchexample.domain.entity.settlement.SettlementDaily;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class PurchaseConfirmedJobConfig {

    private static final String JOB_NAME = "purchaseConfirmedJob";
    private static final int CHUNK_SIZE = 500;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job purchaseConfirmedJob(
            @Qualifier("purchaseConfirmedJobStep") Step purchaseConfirmedJobStep,
            @Qualifier("dailySettlementJobStep") Step dailySettlementJobStep,
            @Qualifier("claimSettlementJobStep") Step claimSettlementJobStep
    ) {
        return new JobBuilder(JOB_NAME, jobRepository)
                .start(purchaseConfirmedJobStep)
                .next(dailySettlementJobStep)
                .next(claimSettlementJobStep)
                .build();
    }

    @Bean
    @JobScope
    public Step purchaseConfirmedJobStep(
            @Qualifier("deliveryCompletedJpaItemReader") JpaPagingItemReader<OrderItem> deliveryCompletedJpaItemReader,
            ItemWriter<OrderItem> purchasedConfirmWriter
    ) {
        return new StepBuilder(JOB_NAME + ":" + "purchaseConfirmed", jobRepository)
                .<OrderItem, OrderItem>chunk(CHUNK_SIZE, transactionManager)
                .reader(deliveryCompletedJpaItemReader)
                .writer(purchasedConfirmWriter)
                .build();
    }

    @Bean
    @JobScope
    public Step dailySettlementJobStep(
            @Qualifier("dailySettlementJpaItemReader") JpaPagingItemReader<OrderItem> dailySettlementJpaItemReader,
            SettlementDailyItemProcessor settlementDailyItemProcessor,
            SettlementDailyItemWriter settlementDailyItemWriter
    ) {
        return new StepBuilder(JOB_NAME + ":" + "dailySettlement", jobRepository)
                .<OrderItem, SettlementDaily>chunk(CHUNK_SIZE, transactionManager)
                .reader(dailySettlementJpaItemReader)
                .processor(settlementDailyItemProcessor)
                .writer(settlementDailyItemWriter)
                .build();
    }

    @Bean
    @JobScope
    public Step claimSettlementJobStep(
            @Qualifier("claimSettlementJpaItemReader")
            JpaPagingItemReader<ClaimItem> claimSettlementJpaItemReader,
            ClaimSettlementItemProcessor claimSettlementItemProcessor,
            ClaimSettlementItemWriter claimSettlementItemWriter
    ) {
        return new StepBuilder(JOB_NAME + ":" + "claimSettlement", jobRepository)
                .<ClaimItem, SettlementDaily>chunk(CHUNK_SIZE, transactionManager)
                .reader(claimSettlementJpaItemReader)
                .processor(claimSettlementItemProcessor)
                .writer(claimSettlementItemWriter)
                .build();
    }
}

