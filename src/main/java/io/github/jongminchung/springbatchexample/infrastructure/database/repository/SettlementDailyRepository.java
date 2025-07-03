package io.github.jongminchung.springbatchexample.infrastructure.database.repository;

import io.github.jongminchung.springbatchexample.domain.entity.settlement.SettlementDaily;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettlementDailyRepository extends JpaRepository<SettlementDaily, Long> {
}
