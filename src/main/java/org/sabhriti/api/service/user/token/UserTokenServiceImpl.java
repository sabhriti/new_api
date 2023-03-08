package org.sabhriti.api.service.user.token;

import lombok.RequiredArgsConstructor;
import org.sabhriti.api.dal.model.user.UserToken;
import org.sabhriti.api.dal.model.user.User;
import org.sabhriti.api.dal.repository.UserTokenRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserTokenServiceImpl implements UserTokenService {

    private final UserTokenRepository userTokenRepository;

    @Override
    public Mono<UserToken> createFor(User user, String reason, LocalDateTime expiresOn) {
        var userToken = new UserToken();
        userToken.setCreatedAt(LocalDateTime.now());
        userToken.setUserId(user.getId());
        userToken.setUsage(reason);
        userToken.setExpiresOn(expiresOn);
        userToken.setToken(UUID.randomUUID().toString());

        return this.userTokenRepository.save(userToken);
    }
}
