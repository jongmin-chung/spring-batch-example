package io.github.jongminchung.springbatchexample.itemList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBatchTest
@SpringBootTest(
        properties = {
            "spring.batch.job.name=" + ItemListJobConfiguration.JOB_NAME,
            "spring.batch.jdbc.initialize-schema=always"
        }
)
class ItemListWriterTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ProcessedItemRepository processedItemRepository;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    @Qualifier(ItemListJobConfiguration.JOB_NAME)
    Job job;

    @Test
    @DisplayName("Processor에서 Writer에서 List를 리턴한다.")
    void test_20250705_1() throws Exception {
        var item1 = Item.builder().name("Item 1").build();
        var item2 = Item.builder().name("Item 2").build();
        var item3 = Item.builder().name("Item 3").build();
        itemRepository.saveAll(List.of(item1, item2, item3));

        // when
        var jobExecution = jobLauncher.run(job, new JobParameters());

        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
        assertThat(processedItemRepository.findAll()).hasSize(9);
    }
}

