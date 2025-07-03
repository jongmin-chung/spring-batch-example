package io.github.jongminchung.springbatchexample.core.job.purchaseconfirmed.claim;

import io.github.jongminchung.springbatchexample.domain.entity.settlement.SettlementDaily;
import io.github.jongminchung.springbatchexample.infrastructure.database.repository.SettlementDailyRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClaimSettlementItemWriter implements ItemWriter<SettlementDaily> {

    private final SettlementDailyRepository settlementDailyRepository;

    @Override
    public void write(Chunk<? extends SettlementDaily> chunk) {
        for (val item : chunk) {
            settlementDailyRepository.save(item);
        }
    }
}
