package org.sabhriti.api.dal.model.question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionFramework {
    @Id
    private String id;

    private String title;

    private String description;

    private String remarks;

    private List<QuestionCategory> categories;
}
