package com.refreshing.beer.beerservice.events;

import com.refreshing.beer.beerservice.web.model.BeerDTO;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@Builder
public class BeerEvent implements Serializable {

    static final long serialVersionUID = 4567385095264487200L;

    private final BeerDTO beerDTO;
}
