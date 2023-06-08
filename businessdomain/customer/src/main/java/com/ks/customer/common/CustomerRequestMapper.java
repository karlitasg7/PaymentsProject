package com.ks.customer.common;

import com.ks.customer.dto.CustomerRequest;
import com.ks.customer.entities.Customer;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CustomerRequestMapper {

    @Mappings({
            @Mapping(source = "code", target = "code"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "phone", target = "phone"),
            @Mapping(source = "iban", target = "iban"),
            @Mapping(source = "surname", target = "surname"),
            @Mapping(source = "address", target = "address"),
            @Mapping(source = "products", target = "products")
    })
    Customer fromRequest(CustomerRequest request);

    @InheritConfiguration
    CustomerRequest fromEntity(Customer customer);

}
