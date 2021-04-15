package com.refreshing.beer.web.mappers;

import com.refreshing.beer.domain.Beer;
import com.refreshing.beer.services.inventory.BeerInventoryService;
import com.refreshing.beer.web.model.BeerDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(uses = {DateMapper.class})
public abstract class BeerMapper {

    public abstract BeerDTO beerToBeerDTO(Beer beer);

    public abstract Beer beerDtoToBeer(BeerDTO beerDTO);

    private BeerInventoryService beerInventoryService;

    private Boolean mapInventoryOnHand = false;

    @AfterMapping
    public void setQuantityOnHand(Beer beer, @MappingTarget BeerDTO.BeerDTOBuilder beerDTO) {
        if (mapInventoryOnHand) {
            beerDTO.quantityOnHand(beerInventoryService.getOnHandQuantityByUpc(beer.getUpc()));
        }
    }

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }

    public void setMapInventoryOnHand(Boolean mapInventoryOnHand) {
        this.mapInventoryOnHand = mapInventoryOnHand;
    }
}