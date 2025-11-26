package com.api.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseRequest(
        @NotNull(message = "product is required")
        Integer productId,
        @Positive(message = "quantity should be at least 1 or more items")
        double quantity

) {
}
