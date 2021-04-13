package com.refreshing.beer.beerservice.events;

import com.refreshing.beer.beerservice.web.model.BeerDTO;

public class InventoryEvent extends BeerEvent {

    public InventoryEvent(BeerDTO beerDTO) {
        super(beerDTO);
    }
}
