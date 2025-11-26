package com.api.ecommerce.orderline;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderLineRequest(
        Integer id,
     Integer orderId,

     @NotNull(message = "product is required")
     Integer productId,

     @Positive(message = "quantity should be at least 1 or more items")
     double quantity
)
{
}
