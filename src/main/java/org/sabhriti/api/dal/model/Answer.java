package org.sabhriti.api.dal.model;

import lombok.Data;

import java.util.List;

@Data
public class Answer {
    private String value;
    private List<LocalizedText> texts;
}
