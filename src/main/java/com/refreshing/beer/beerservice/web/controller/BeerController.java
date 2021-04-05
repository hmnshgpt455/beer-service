package com.refreshing.beer.beerservice.web.controller;

import com.refreshing.beer.beerservice.services.BeerService;
import com.refreshing.beer.beerservice.web.model.BeerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor //This will inject the dependencies automatically because we have made them final
@RequestMapping(BeerController.API_V1_BEER)
public class BeerController {

    public static final String API_V1_BEER = "/api/v1/beer";
    private final BeerService beerService;

    @GetMapping("/{beerId}")
    @ResponseStatus(HttpStatus.OK)
    public BeerDTO getBeerById(@PathVariable("beerId") UUID beerId) {
        return beerService.getBeerById(beerId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BeerDTO saveNewBeer(@RequestBody @Validated BeerDTO beerDTO) {
        return beerService.saveNewBeer(beerDTO);
    }

    @PutMapping("/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBeerById(@RequestBody @Validated BeerDTO beerDTO, @PathVariable("beerId") UUID beerId) {
        beerService.updateBeer(beerDTO, beerId);
    }

}
