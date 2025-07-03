package io.github.jongminchung.springbatchexample.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClaimType {
    CANCEL("cancel", "취소"),
    EXCHANGE("exchange", "교환"),
    RETURN("return", "반품"),
    ;

    private final String code;
    private final String description;
}

