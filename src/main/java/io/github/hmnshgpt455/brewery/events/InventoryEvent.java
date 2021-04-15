package io.github.hmnshgpt455.brewery.events;

import io.github.hmnshgpt455.brewery.model.BeerDTO;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InventoryEvent extends BeerEvent {

    public InventoryEvent(BeerDTO beerDTO) {
        super(beerDTO);
    }
}
