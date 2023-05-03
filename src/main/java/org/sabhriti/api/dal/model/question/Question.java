package org.sabhriti.api.dal.model.question;

import lombok.Data;
import org.sabhriti.api.dal.model.Answer;
import org.sabhriti.api.dal.model.LocalizedText;
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
