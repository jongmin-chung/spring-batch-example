package io.github.jongminchung.springbatchexample.infrastructure.database.repository;

import io.github.jongminchung.springbatchexample.domain.entity.claim.ClaimItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimItemRepository extends JpaRepository<ClaimItem, Long> {
}
