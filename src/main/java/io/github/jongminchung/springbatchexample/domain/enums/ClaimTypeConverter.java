package io.github.jongminchung.springbatchexample.domain.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ClaimTypeConverter implements AttributeConverter<ClaimType, String> {

    @Override
    public String convertToDatabaseColumn(ClaimType attribute) {
        return attribute.getCode();
    }

    @Override
    public ClaimType convertToEntityAttribute(String dbData) {
        return switch (dbData) {
            case "cancel" -> ClaimType.CANCEL;
            case "exchange" -> ClaimType.EXCHANGE;
            case "return" -> ClaimType.RETURN;
            default -> throw new IllegalArgumentException("Unknown ClaimType code: " + dbData);
        };
    }
}
