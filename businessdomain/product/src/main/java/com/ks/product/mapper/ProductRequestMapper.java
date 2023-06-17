package com.ks.product.mapper;

import com.ks.product.dto.ProductRequest;
import com.ks.product.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductRequestMapper {

    @Mappings({
            @Mapping(source = "code", target = "code"),
            @Mapping(source = "name", target = "name")
    })
    Product fromRequest(ProductRequest request);

}
