package org.sabhriti.api.dal.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Factory {
    @Id
    private String id;

    private String name;

    private FactoryLocation location;
}
