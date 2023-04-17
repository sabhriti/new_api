package org.sabhriti.api.dal.model;

import lombok.Data;

@Data
public class Address {

    private String street;

    private String houseNumber;

    private Integer zipCode;

    private String city;

    private String country;
}
