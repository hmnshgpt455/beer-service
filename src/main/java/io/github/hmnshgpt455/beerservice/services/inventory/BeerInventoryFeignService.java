package io.github.hmnshgpt455.beerservice.services.inventory;

import io.github.hmnshgpt455.brewery.model.BeerInventoryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Profile("local-discovery")
@Slf4j
public class BeerInventoryFeignService implements BeerInventoryService {

    private final InventoryServiceFeignClient inventoryServiceFeignClient;

    @Override
    public Integer getOnHandQuantity(UUID beerId) {
        return null;
    }

    @Override
    public Integer getOnHandQuantityByUpc(String upc) {
        log.debug("Calling Inventory service - beer upc " + upc);

        ResponseEntity<List<BeerInventoryDTO>> responseEntity = inventoryServiceFeignClient.getOnHandInventory(upc);

        Integer onHand = Objects.requireNonNull(responseEntity.getBody())
                .stream()
                .mapToInt(BeerInventoryDTO::getQuantityOnHand)
                .sum();

        log.debug("Beer UPC : " + upc + "On hand is: " + onHand);
        return onHand;
    }
}
