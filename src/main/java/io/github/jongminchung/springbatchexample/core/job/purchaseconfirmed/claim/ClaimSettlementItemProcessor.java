package io.github.jongminchung.springbatchexample.core.job.purchaseconfirmed.claim;

import io.github.jongminchung.springbatchexample.domain.entity.claim.ClaimItem;
import io.github.jongminchung.springbatchexample.domain.entity.settlement.SettlementDaily;
import io.github.jongminchung.springbatchexample.domain.utils.NegativeDailySettlementCollection;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ClaimSettlementItemProcessor implements ItemProcessor<ClaimItem, SettlementDaily> {

    @Override
    public SettlementDaily process(ClaimItem item) {
        return new NegativeDailySettlementCollection(item).getSettlementDaily();
    }
}
