package org.sabhriti.api.web.dto;

public record ApiResponse(int status, String message, Object result) {}
