package com.refreshing.beer.beerservice.services;

import com.refreshing.beer.beerservice.repositories.BeerRepository;
import com.refreshing.beer.beerservice.web.controller.NotFoundException;
import com.refreshing.beer.beerservice.web.mappers.BeerMapper;
import com.refreshing.beer.beerservice.web.model.BeerDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BeerServiceImpl implements BeerService {

    public static final String BEER_WITH_GIVEN_ID_NOT_FOUND = "BEER_WITH_GIVEN_ID_NOT_FOUND";
    public static final String CANNOT_FIND_BEER_WITH_GIVEN_ID = "Cannot find beer with given id";
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    public BeerServiceImpl(BeerRepository beerRepository, BeerMapper beerMapper) {
        this.beerRepository = beerRepository;
        this.beerMapper = beerMapper;
    }

    @Override
    public BeerDTO getBeerById(UUID beerId) {
        return beerMapper.beerToBeerDTO(beerRepository.findById(beerId)
                .orElseThrow(() -> new NotFoundException(BEER_WITH_GIVEN_ID_NOT_FOUND, CANNOT_FIND_BEER_WITH_GIVEN_ID)));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {
        return saveAndReturnBeer(beerDTO);
    }

    private BeerDTO saveAndReturnBeer(BeerDTO beerDTO) {
        return beerMapper.beerToBeerDTO(beerRepository.save(beerMapper.beerDtoToBeer(beerDTO)));
    }

    @Override
    public void updateBeer(BeerDTO beerDTO, UUID beerId) {
        if (!beerRepository.existsById(beerId)) {
            throw new NotFoundException(BEER_WITH_GIVEN_ID_NOT_FOUND, CANNOT_FIND_BEER_WITH_GIVEN_ID);
        }

        beerDTO.setId(beerId);
        saveAndReturnBeer(beerDTO);
    }
}
