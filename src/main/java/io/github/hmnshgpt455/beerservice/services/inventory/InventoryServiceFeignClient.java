package io.github.hmnshgpt455.beerservice.services.inventory;

import io.github.hmnshgpt455.brewery.model.BeerInventoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "beer-inventory-service")
public interface InventoryServiceFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = BeerInventoryServiceRestTemplateImpl.INVENTORY_API_WITH_BEER_UPC_PATH)
    ResponseEntity<List<BeerInventoryDTO>> getOnHandInventory(@PathVariable("upc") String upc);
}
