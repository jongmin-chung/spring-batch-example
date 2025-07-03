package io.github.jongminchung.springbatchexample.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClaimCompleteService {

    private final ClaimCompleteExecutor executor;

    public void execute(Long claimId) {
        executor.updateCompleteAt(claimId);
    }
}
