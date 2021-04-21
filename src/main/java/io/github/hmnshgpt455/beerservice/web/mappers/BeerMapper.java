package io.github.hmnshgpt455.beerservice.web.mappers;

import io.github.hmnshgpt455.beerservice.domain.Beer;
import io.github.hmnshgpt455.beerservice.services.inventory.BeerInventoryService;
import io.github.hmnshgpt455.brewery.model.BeerDTO;
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
