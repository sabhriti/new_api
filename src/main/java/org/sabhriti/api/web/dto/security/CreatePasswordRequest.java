package org.sabhriti.api.web.dto.security;

public record CreatePasswordRequest (String oldPassword, String password, String token){}
