package io.github.jongminchung.springbatchexample.core.job.purchaseconfirmed.settlement;

import io.github.jongminchung.springbatchexample.domain.entity.settlement.SettlementDaily;
import io.github.jongminchung.springbatchexample.infrastructure.database.repository.SettlementDailyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SettlementDailyItemWriter implements ItemWriter<SettlementDaily> {

    private final SettlementDailyRepository settlementDailyRepository;

    @Override
    public void write(Chunk<? extends SettlementDaily> chunk) throws Exception {
        for (var item : chunk) {
            settlementDailyRepository.save(item);
        }
    }
}
