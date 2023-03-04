package org.sabhriti.api.service.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityContextRepository implements ServerSecurityContextRepository {

    private static final String TOKEN_PREFIX = "Bearer ";

    private final AuthenticationManager authenticationManager;

    public Mono<Void> save(ServerWebExchange swe, SecurityContext sc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange serverWebExchange) {
        var authHeader = this.extractAuthenticationHeader(serverWebExchange);

        if (authHeader != null && authHeader.startsWith(TOKEN_PREFIX)) {
            var authToken = authHeader.replace(TOKEN_PREFIX, "");
            return this.authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authToken, authToken))
                    .map(SecurityContextImpl::new);
        }

        return Mono.empty();
    }

	private String extractAuthenticationHeader(ServerWebExchange serverWebExchange) {
		return serverWebExchange
				.getRequest()
				.getHeaders()
				.getFirst(HttpHeaders.AUTHORIZATION);
	}
}
