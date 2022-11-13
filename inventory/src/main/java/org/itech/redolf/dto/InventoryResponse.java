package org.itech.redolf.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponse {
    private int id;
    private String skuCode;
    private Integer quantity;
    private boolean isInStock;
}
