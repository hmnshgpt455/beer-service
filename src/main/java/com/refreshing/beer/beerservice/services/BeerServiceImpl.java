package com.refreshing.beer.beerservice.services;

import com.refreshing.beer.beerservice.domain.Beer;
import com.refreshing.beer.beerservice.repositories.BeerRepository;
import com.refreshing.beer.beerservice.web.controller.NotFoundException;
import com.refreshing.beer.beerservice.web.mappers.BeerMapper;
import com.refreshing.beer.beerservice.web.model.BeerDTO;
import com.refreshing.beer.beerservice.web.model.BeerPageList;
import com.refreshing.beer.beerservice.web.model.BeerStyleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
        Optional<Beer> beerOptional = beerRepository.findById(beerId);

        if (beerOptional.isEmpty()) {
            throw new NotFoundException(BEER_WITH_GIVEN_ID_NOT_FOUND, CANNOT_FIND_BEER_WITH_GIVEN_ID);
        }
        Beer beer = beerOptional.get();
        beer.setBeerName(beerDTO.getBeerName());
        beer.setBeerStyle(beerDTO.getBeerStyle().toString());
        beer.setUpc(beerDTO.getUpc());
        beer.setPrice(beerDTO.getPrice());
        beerRepository.save(beer);
    }

    @Override
    public BeerPageList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest) {

        Page<Beer> beerPage = getBeerPage(beerName, beerStyle, pageRequest);
        return new BeerPageList(beerPage
                .getContent()
                .stream()
                .map(beerMapper::beerToBeerDTO)
                .collect(Collectors.toList()),
                PageRequest.of(beerPage.getPageable().getPageNumber(),
                        beerPage.getPageable().getPageSize()),
                beerPage.getTotalElements());
    }

    private Page<Beer> getBeerPage(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest) {

        if (!ObjectUtils.isEmpty(beerName) && !ObjectUtils.isEmpty(beerStyle)) {
            return beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        }

        if (ObjectUtils.isEmpty(beerName) && !ObjectUtils.isEmpty(beerStyle)) {
            return beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        }

        if (!ObjectUtils.isEmpty(beerName) && ObjectUtils.isEmpty(beerStyle)) {
            return beerRepository.findAllByBeerName(beerName, pageRequest);
        }

        return beerRepository.findAll(pageRequest);
    }
}
