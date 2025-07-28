package com.skysoft.linkedin.connection_service.entity;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Node("Person")
@Data
public class PersonEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private String name;
}
