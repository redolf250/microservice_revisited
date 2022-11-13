package org.itech.redolf.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineItemsRequest {
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
