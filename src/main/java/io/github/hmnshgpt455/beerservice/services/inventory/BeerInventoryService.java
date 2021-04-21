package io.github.hmnshgpt455.beerservice.services.inventory;

import java.util.UUID;

public interface BeerInventoryService {

    Integer getOnHandQuantity(UUID beerId);
    Integer getOnHandQuantityByUpc(String upc);

}
