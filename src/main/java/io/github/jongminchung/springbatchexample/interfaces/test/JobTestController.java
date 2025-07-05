package io.github.jongminchung.springbatchexample.interfaces.test;

import io.github.jongminchung.springbatchexample.core.launcher.PurchaseConfirmedRunner;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JobTestController {

    private final PurchaseConfirmedRunner purchaseConfirmedRunner;

    @PostMapping("/settlement/daily")
    public void runDailyJob() {
        purchaseConfirmedRunner.run();
    }
}
