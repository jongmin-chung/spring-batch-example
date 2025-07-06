package io.github.jongminchung.springbatchexample.processLazyException;

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

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBatchTest
@SpringBootTest(
        properties = {
            "spring.batch.job.name=" + ProcessLazyExceptionJobConfiguration.JOB_NAME,
            "spring.batch.jdbc.initialize-schema=always"
        }
)
class PLE_JobTest {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    @Qualifier(ProcessLazyExceptionJobConfiguration.JOB_NAME)
    Job job;

    @Autowired
    PLE_OrderRepository orderRepository;

    @Test
    @DisplayName("Processor에서 Writer에서 List를 리턴한다.")
    void test_20250705_1() throws Exception {
        for (int i = 0; i < 100; i++) {
            orderRepository.save(
                    PLE_Order.builder()
                            .memo("문 앞에 놓아주세요.")
                            .products(List.of(
                                    PLE_Product.builder()
                                            .name("마우스")
                                            .amount(BigDecimal.valueOf(10_000))
                                            .build(),
                                    PLE_Product.builder()
                                            .name("키보드")
                                            .amount(BigDecimal.valueOf(20_000))
                                            .build()
                            ))
                            .build()
            );
        }

        // when
        var jobExecution = jobLauncher.run(job, new JobParameters());

        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
        assertThat(orderRepository.findAll()).hasSize(100);
    }
}

