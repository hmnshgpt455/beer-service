package com.refreshing.beer.beerservice.services;

import com.refreshing.beer.beerservice.web.model.BeerDTO;

import java.util.UUID;

public interface BeerService {
    BeerDTO getBeerById(UUID beerId);

    BeerDTO saveNewBeer(BeerDTO beerDTO);

    void updateBeer(BeerDTO beerDTO, UUID beerId);
}
