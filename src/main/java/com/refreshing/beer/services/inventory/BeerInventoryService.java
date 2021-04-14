package com.refreshing.beer.services.inventory;

import java.util.UUID;

public interface BeerInventoryService {

    Integer getOnHandQuantity(UUID beerId);
    Integer getOnHandQuantityByUpc(String upc);

}
