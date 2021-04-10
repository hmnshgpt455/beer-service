package com.refreshing.beer.beerservice.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerInventoryDTO implements Serializable {

    static final long serialVersionUID = -4068590285854206238L;

    private UUID id;
    private OffsetDateTime createdDate;
    private OffsetDateTime lastModifiedDate;
    private UUID beerId;
    private Integer quantityOnHand;
}
