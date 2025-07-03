package io.github.jongminchung.springbatchexample.infrastructure.database;

import io.github.jongminchung.springbatchexample.domain.ClaimCompleteExecutor;
import io.github.jongminchung.springbatchexample.domain.enums.ClaimStatus;
import io.github.jongminchung.springbatchexample.infrastructure.database.repository.ClaimReceiptRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ClaimCompleteJpaExecutor implements ClaimCompleteExecutor {

    private final ClaimReceiptRepository claimReceiptRepository;

    @Override
    public void updateCompleteAt(Long claimId) {
        val claimReceipt = claimReceiptRepository.findById(claimId)
                .orElseThrow(() -> new IllegalArgumentException("Claim not found with id: " + claimId));

        val updateReceipt = claimReceipt.toBuilder()
                .id(claimId)
                .completeAt(LocalDateTime.now())
                .claimStatus(ClaimStatus.COMPLETED)
                .build();

        claimReceiptRepository.save(updateReceipt);
    }
}
