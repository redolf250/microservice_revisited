package org.itech.redolf.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
@Document(collection = "products")
public class Product {

    @Transient
    public static final String SEQUENCE_NAME = "product_sequence";

    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;

}
