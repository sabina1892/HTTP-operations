package org.example.spring6restmvc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
public class Food {
    private UUID id;
    private String name;
    private Integer count;
    private Integer price;
    private String country;
}
