package org.sabhriti.api.dal.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
public class Survey {
    @Id
    private String id;

    private String surveyName;

    private String description;

    private List<String> questions;

    private String factoryId;
}
