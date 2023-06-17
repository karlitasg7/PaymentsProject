package com.ks.product.mapper;

import com.ks.product.dto.ProductResponse;
import com.ks.product.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductResponseMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "code", target = "code"),
            @Mapping(source = "name", target = "name")
    })
    ProductResponse fromEntity(Product product);

    List<ProductResponse> fromList(List<Product> productList);

}
