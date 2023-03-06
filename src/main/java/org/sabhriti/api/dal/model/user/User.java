package org.sabhriti.api.dal.model.user;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
public class User {

    @Id
    private String id;

    private String name;

    private String email;

    private String phone;

    @Indexed
    private String username;

    private String password;

    private String activationStatus = UserActivationStatus.INACTIVE;

    private List<Role> roles;
}
