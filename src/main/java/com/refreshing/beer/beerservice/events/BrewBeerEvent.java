package com.refreshing.beer.beerservice.events;

import com.refreshing.beer.beerservice.web.model.BeerDTO;
import lombok.Builder;

public class BrewBeerEvent extends BeerEvent {

    @Builder
    public BrewBeerEvent(BeerDTO beerDTO) {
        super(beerDTO);
    }
}
