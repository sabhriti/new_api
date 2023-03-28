package org.sabhriti.api.web.dto;

import lombok.Builder;

@Builder
public record ErrorResponse(String message) {}
