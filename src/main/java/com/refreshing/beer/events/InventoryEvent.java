package com.refreshing.beer.events;

import com.refreshing.beer.web.model.BeerDTO;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InventoryEvent extends BeerEvent {

    public InventoryEvent(BeerDTO beerDTO) {
        super(beerDTO);
    }
}
