package io.github.jongminchung.springbatchexample.itemList;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@Configuration
@RequiredArgsConstructor
public class ItemListJobConfiguration {

    public static final String JOB_NAME = "itemListJob";
    static final String STEP_NAME = "itemListStep";

    private final EntityManagerFactory entityManagerFactory;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    @Bean(name = JOB_NAME)
    public Job job() {
        return new JobBuilder(JOB_NAME, jobRepository).start(step()).build();
    }

    private Step step() {
        return new StepBuilder(STEP_NAME, jobRepository)
                .<Item, List<ProcessedItem>>chunk(2, platformTransactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writerForList())
                .build();
    }

    private ItemProcessor<Item, List<ProcessedItem>> processor() {
        AtomicInteger idx = new AtomicInteger(1);
        return item -> List.of(
                ProcessedItem.builder()
                        .processedName("Processed: " + item.getName() + " " + idx.getAndIncrement())
                        .build(),
                ProcessedItem.builder()
                        .processedName("Processed: " + item.getName() + " " + idx.getAndIncrement())
                        .build(),
                ProcessedItem.builder()
                        .processedName("Processed: " + item.getName() + " " + idx.getAndIncrement())
                        .build()
        );
    }

    private JpaPagingItemReader<Item> reader() {
        JpaPagingItemReader<Item> reader = new JpaPagingItemReader<>();
        reader.setQueryString("SELECT i FROM Item i");
        reader.setEntityManagerFactory(entityManagerFactory);
        return reader;
    }

    private JpaItemWriter<ProcessedItem> writer() {
        JpaItemWriter<ProcessedItem> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    private JpaItemListWriter<ProcessedItem> writerForList() {
        return new JpaItemListWriter<>(writer());
    }
}

@RequiredArgsConstructor
class JpaItemListWriter<T> extends JpaItemWriter<List<T>> {
    private final JpaItemWriter<T> delegate;

    @Override
    public void write(Chunk<? extends List<T>> items) {
        var processedItems = items.getItems().stream()
                .flatMap(List::stream)
                .toList();

        delegate.write(new Chunk<>(processedItems));
    }
}

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
class ProcessedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String processedName;
}

interface ItemRepository extends JpaRepository<Item, Long> {}

interface ProcessedItemRepository extends JpaRepository<ProcessedItem, Long> {}

