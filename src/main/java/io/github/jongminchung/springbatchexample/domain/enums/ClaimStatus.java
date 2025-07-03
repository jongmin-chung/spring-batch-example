package io.github.jongminchung.springbatchexample.domain.enums;

import lombok.Getter;

@Getter
public enum ClaimStatus {
    WITHDRAW,
    RECEIVED,
    IN_PROGRESS,
    COMPLETED,
}
