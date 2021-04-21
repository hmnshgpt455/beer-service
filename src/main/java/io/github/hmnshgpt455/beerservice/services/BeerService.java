package io.github.hmnshgpt455.beerservice.services;

import io.github.hmnshgpt455.brewery.model.BeerDTO;
import io.github.hmnshgpt455.brewery.model.BeerPageList;
import io.github.hmnshgpt455.brewery.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {
    BeerDTO getBeerById(UUID beerId, Boolean showInventoryOnHand);

    BeerDTO saveNewBeer(BeerDTO beerDTO);

    void updateBeer(BeerDTO beerDTO, UUID beerId);

    BeerPageList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand);

    BeerDTO getBeerByUpcCode(String upc, Boolean showInventoryOnHand);
}
