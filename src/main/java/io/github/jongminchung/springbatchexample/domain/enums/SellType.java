package io.github.jongminchung.springbatchexample.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SellType {
    PURCHASE("매입"),
    CONSIGNMENT("위탁"),
    ;

    private final String description;
}
