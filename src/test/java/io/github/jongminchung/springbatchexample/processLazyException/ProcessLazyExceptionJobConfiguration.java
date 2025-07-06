package io.github.jongminchung.springbatchexample.processLazyException;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.PlatformTransactionManager;

import java.math.BigDecimal;
import java.util.List;


@Configuration
@RequiredArgsConstructor
public class ProcessLazyExceptionJobConfiguration {

    public static final String JOB_NAME = "processLazyExceptionJob";
    static final String STEP_NAME = "step";
    static final int CHUNK_SIZE = 100;

    private final EntityManagerFactory entityManagerFactory;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    @Bean(name = JOB_NAME)
    public Job job() {
        return new JobBuilder(JOB_NAME, jobRepository).start(step()).build();
    }

    private Step step() {
        return new StepBuilder(STEP_NAME, jobRepository)
                .<PLE_Order, PLE_History>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    private JpaPagingItemReader<PLE_Order> reader() {
        var reader = new JpaPagingItemReader<PLE_Order>();
        reader.setQueryString("SELECT o FROM PLE_Order o");
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setPageSize(CHUNK_SIZE); // 정하지 않아도 되지만 기본값이 10이라서 명시적으로 설정
        return reader;
    }

    private ItemProcessor<PLE_Order, PLE_History> processor() {
        return item -> PLE_History.builder()
                .orderId(item.getId())
                .products(item.getProducts())
                .build();
    }

    private JpaItemWriter<PLE_History> writer() {
        var writer = new JpaItemWriter<PLE_History>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
class PLE_Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal amount;
}

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
class PLE_Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String memo;

    @OneToMany(cascade = CascadeType.ALL)
    private List<PLE_Product> products;
}

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
class PLE_History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<PLE_Product> products;
}

interface PLE_ProductRepository extends JpaRepository<PLE_Product, Long> {}

interface PLE_OrderRepository extends JpaRepository<PLE_Order, Long> {}

interface PLE_HistoryRepository extends JpaRepository<PLE_History, Long> {}
