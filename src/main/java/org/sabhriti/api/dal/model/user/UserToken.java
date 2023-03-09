package org.sabhriti.api.dal.model.user;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class UserToken {
    @Id
    private String id;

    private String token;

    private String userId;

    private LocalDateTime createdAt;

    private LocalDateTime expiresOn;

    private String usage;

    private Boolean isUsed;

    private LocalDateTime usedAt;
}
