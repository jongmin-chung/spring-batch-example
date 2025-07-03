package io.github.jongminchung.springbatchexample.core.job.purchaseconfirmed.settlement;

import io.github.jongminchung.springbatchexample.domain.entity.order.OrderItem;
import io.github.jongminchung.springbatchexample.domain.entity.settlement.SettlementDaily;
import io.github.jongminchung.springbatchexample.domain.utils.PositiveDailySettlementCollection;
import lombok.val;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class SettlementDailyItemProcessor implements ItemProcessor<OrderItem, SettlementDaily> {

    @Override
    public SettlementDaily process(OrderItem item) throws Exception {
        val positiveDailySettlementCollection = new PositiveDailySettlementCollection(item);

        return positiveDailySettlementCollection.getSettlementDaily();
    }
}
