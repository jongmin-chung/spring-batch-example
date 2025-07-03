package io.github.jongminchung.springbatchexample.interfaces;

import io.github.jongminchung.springbatchexample.domain.ClaimCompleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ClaimCompleteController {

    private final ClaimCompleteService claimCompleteService;

    @PostMapping("/claims/{claimId}/complete")
    public void execute(@PathVariable Long claimId) {
        claimCompleteService.execute(claimId);
    }
}
