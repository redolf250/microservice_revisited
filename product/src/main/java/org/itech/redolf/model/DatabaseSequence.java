package org.itech.redolf.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
@Document(collection = "db_sequence")
public class DatabaseSequence {

    @Id
    private String id;
    private int sequence_number;
}
