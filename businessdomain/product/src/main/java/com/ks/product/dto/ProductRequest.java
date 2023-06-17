package com.ks.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "ProductRequest", description = "model to create a product")
@Data
public class ProductRequest {
    private String code;
    private String name;
}
