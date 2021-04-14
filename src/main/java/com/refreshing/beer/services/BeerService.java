package com.refreshing.beer.services;

import com.refreshing.beer.web.model.BeerDTO;
import com.refreshing.beer.web.model.BeerPageList;
import com.refreshing.beer.web.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {
    BeerDTO getBeerById(UUID beerId, Boolean showInventoryOnHand);

    BeerDTO saveNewBeer(BeerDTO beerDTO);

    void updateBeer(BeerDTO beerDTO, UUID beerId);

    BeerPageList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand);

    BeerDTO getBeerByUpcCode(String upc, Boolean showInventoryOnHand);
}
