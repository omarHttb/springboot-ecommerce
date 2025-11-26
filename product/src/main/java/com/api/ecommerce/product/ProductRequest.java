package com.api.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(
         Integer id,
         @NotNull(message = "name is required")
         String name,
         @NotNull(message = "description is required")
         String description,
         @NotNull(message = "quantity is required")
         double quantity,
         @Positive(message = "price should be positive")
         BigDecimal price,
         @NotNull(message = "category id is required")
         Integer categoryId
) {
}
