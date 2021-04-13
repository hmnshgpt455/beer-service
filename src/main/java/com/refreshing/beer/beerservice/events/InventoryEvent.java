package com.refreshing.beer.beerservice.events;

import com.refreshing.beer.beerservice.web.model.BeerDTO;
import lombok.Builder;

public class InventoryEvent extends BeerEvent {

    @Builder
    public InventoryEvent(BeerDTO beerDTO) {
        super(beerDTO);
    }
}
