package io.github.jongminchung.springbatchexample.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TaxType {
    TAX("과세"),
    FREE("면세"),
    ZERO("영세");

    private final String description;
}
