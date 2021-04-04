package com.refreshing.beer.beerservice.web.mappers;

import com.refreshing.beer.beerservice.domain.Beer;
import com.refreshing.beer.beerservice.web.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

    BeerDTO beerToBeerDTO(Beer beer);

    Beer beerDtoToBeer(BeerDTO beerDTO);
}
