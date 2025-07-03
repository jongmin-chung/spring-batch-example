package io.github.jongminchung.springbatchexample.core.job.total;

import io.github.jongminchung.springbatchexample.domain.entity.settlement.SettlementTotal;
import io.github.jongminchung.springbatchexample.infrastructure.database.repository.SettlementTotalRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TotalSettlementItemWriter implements ItemWriter<SettlementTotal> {

    private final SettlementTotalRepository settlementTotalRepository;

    @Override
    public void write(Chunk<? extends SettlementTotal> chunk) throws Exception {
        for (val settlementTotal : chunk) {
            settlementTotalRepository.save(settlementTotal);
        }
    }
}
