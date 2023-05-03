package org.sabhriti.api.dal.model.question;

import lombok.Data;
import org.sabhriti.api.dal.model.LocalizedText;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class QuestionCategory {
    @Id
    private String id;

    private List<LocalizedText> name;

    private String frameworkId;

    private String description;

    private String remarks;
}
