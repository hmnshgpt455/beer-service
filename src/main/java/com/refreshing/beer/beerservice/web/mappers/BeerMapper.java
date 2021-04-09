package com.refreshing.beer.beerservice.web.mappers;

import com.refreshing.beer.beerservice.domain.Beer;
import com.refreshing.beer.beerservice.services.inventory.BeerInventoryService;
import com.refreshing.beer.beerservice.web.model.BeerDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(uses = {DateMapper.class})
public abstract class BeerMapper {

    public abstract BeerDTO beerToBeerDTO(Beer beer);

    public abstract Beer beerDtoToBeer(BeerDTO beerDTO);

    private BeerInventoryService beerInventoryService;

    @AfterMapping
    public void setQuantityOnHand(Beer beer, @MappingTarget BeerDTO beerDTO) {
        beerDTO.setQuantityOnHand(beerInventoryService.getOnHandQuantity(beer.getId()));
    }

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }
}
