package io.github.jongminchung.springbatchexample.core.launcher;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PurchaseConfirmedRunner {

    private final JobLauncher jobLauncher;
    private final Job purchaseConfirmedJob;

    @SneakyThrows(Exception.class)
    public void run() {
        val jobExecution = jobLauncher.run(purchaseConfirmedJob, new JobParameters());
        log.info("Job execution status: {}", jobExecution.getStatus());
    }
}
