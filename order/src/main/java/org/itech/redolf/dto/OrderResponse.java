package org.itech.redolf.dto;

import lombok.*;
import org.itech.redolf.model.OrderLineItems;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private int id;
    private String orderNumber;
    private List<OrderLineItemsResponse> orderLineItems;
}
