package org.sabhriti.api.dal.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class User {
    @Id
    private String id;
    private String fullName;
    private String address;
    private String telephone;
    private String email;
    private String password;
    private String isActive;
    private String isAdmin;
}
