package org.itech.redolf.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryRequest {
    private String skuCode;
    private Integer quantity;
}
