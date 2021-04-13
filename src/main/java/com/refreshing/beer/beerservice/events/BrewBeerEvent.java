package com.refreshing.beer.beerservice.events;

import com.refreshing.beer.beerservice.web.model.BeerDTO;

public class BrewBeerEvent extends BeerEvent {

    public BrewBeerEvent(BeerDTO beerDTO) {
        super(beerDTO);
    }
}
