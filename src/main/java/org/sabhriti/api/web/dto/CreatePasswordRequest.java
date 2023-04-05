package org.sabhriti.api.web.dto;

public record CreatePasswordRequest (String oldPassword, String password, String token){}
