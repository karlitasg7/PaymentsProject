package com.ks.customer.dto;

import com.ks.customer.entities.CustomerProduct;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(name = "CustomerRequest", description = "model to create a customer")
@Data
public class CustomerRequest {

    private String code;
    private String name;
    private String phone;
    private String iban;
    private String surname;
    private String address;

    private List<CustomerProduct> products;

}
