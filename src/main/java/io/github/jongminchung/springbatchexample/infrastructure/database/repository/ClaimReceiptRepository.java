package io.github.jongminchung.springbatchexample.infrastructure.database.repository;

import io.github.jongminchung.springbatchexample.domain.entity.claim.ClaimReceipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimReceiptRepository extends JpaRepository<ClaimReceipt, Long> {
}
