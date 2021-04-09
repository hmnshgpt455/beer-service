package com.refreshing.beer.beerservice.services.inventory;

import java.util.UUID;

public interface BeerInventoryService {

    Integer getOnHandQuantity(UUID beerId);

}
