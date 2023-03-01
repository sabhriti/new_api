package org.sabhriti.api.dal.model;

import lombok.Data;

@Data
public class LocalizedText {
    private String text;

    private String locale;
}
