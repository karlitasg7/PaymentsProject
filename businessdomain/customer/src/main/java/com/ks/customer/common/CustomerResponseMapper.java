package com.ks.customer.common;

import com.ks.customer.dto.CustomerResponse;
import com.ks.customer.entities.Customer;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerResponseMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "code", target = "code"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "phone", target = "phone"),
            @Mapping(source = "iban", target = "iban"),
            @Mapping(source = "surname", target = "surname"),
            @Mapping(source = "address", target = "address")
    })
    Customer fromRequest(CustomerResponse request);

    @InheritConfiguration
    CustomerResponse fromEntity(Customer customer);

    List<CustomerResponse> fromList(List<Customer> customerList);

}
