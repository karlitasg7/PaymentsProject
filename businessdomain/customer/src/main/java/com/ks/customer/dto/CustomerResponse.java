package com.ks.customer.dto;

import lombok.Data;

@Data
public class CustomerResponse {

    private Long   id;
    private String code;
    private String name;
    private String phone;
    private String iban;
    private String surname;
    private String address;

}
