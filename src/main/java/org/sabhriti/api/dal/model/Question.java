package org.sabhriti.api.dal.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
public class Question {
    @Id
    private String id;

    private Integer number;

    private String type;

    private List<LocalizedText> questionText;

    private List<Answer> answers;
}
