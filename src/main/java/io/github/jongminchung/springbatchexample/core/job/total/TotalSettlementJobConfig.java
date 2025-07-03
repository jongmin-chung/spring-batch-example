package io.github.jongminchung.springbatchexample.core.job.total;

import io.github.jongminchung.springbatchexample.domain.entity.settlement.SettlementTotal;
import io.github.jongminchung.springbatchexample.domain.projection.SummingSettlementResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class TotalSettlementJobConfig {

    public static final String JOB_NAME = "totalSettlementJob";
    public static final int CHUNK_SIZE = 500;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job totalSettlementJob(
            @Qualifier("totalSettlementStep") Step totalSettlementStep
    ) {
        return new JobBuilder(JOB_NAME, jobRepository)
                .start(totalSettlementStep)
                .build();
    }

    @Bean
    @JobScope
    public Step totalSettlementStep(
            @Qualifier("totalSettlementJpaItemReader")
            JpaPagingItemReader<SummingSettlementResponse> totalSettlementJpaItemReader,
            TotalSettlementItemProcessor totalSettlementItemProcessor,
            TotalSettlementItemWriter totalSettlementItemWriter
    ) {
        return new StepBuilder(JOB_NAME + ":step", jobRepository)
                .<SummingSettlementResponse, SettlementTotal>chunk(CHUNK_SIZE, transactionManager)
                .reader(totalSettlementJpaItemReader)
                .processor(totalSettlementItemProcessor)
                .writer(totalSettlementItemWriter)
                .build();
    }
}
