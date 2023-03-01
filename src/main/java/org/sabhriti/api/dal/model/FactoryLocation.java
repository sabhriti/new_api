package org.sabhriti.api.dal.model;

import lombok.Data;

@Data
public class FactoryLocation {

    private String street;

    private String houseNumber;

    private Integer zipCode;

    private String city;

    private String country;
}
