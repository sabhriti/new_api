package org.sabhriti.api.dal.model.question;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class QuestionFramework {
    @Id
    private String id;

    private String name;

    private String description;

    private String remarks;
}
