package org.sabhriti.api.web.dto;

import lombok.Builder;

import java.util.Map;

@Builder
public record ErrorResponse (Integer code, String message, Map<String, String> validationMessages) {
    public ErrorResponse(Integer code, String message) {
        this(code, message, Map.of());
    }

    public ErrorResponse(Integer code,Map<String, String> validationMessages) {
        this(code, "data validation failed", validationMessages);
    }
}
