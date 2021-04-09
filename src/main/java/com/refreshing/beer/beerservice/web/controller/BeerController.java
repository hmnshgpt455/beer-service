package com.refreshing.beer.beerservice.web.controller;

import com.refreshing.beer.beerservice.services.BeerService;
import com.refreshing.beer.beerservice.web.model.BeerDTO;
import com.refreshing.beer.beerservice.web.model.BeerPageList;
import com.refreshing.beer.beerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.UUID;

@RestController
@RequiredArgsConstructor //This will inject the dependencies automatically because we have made them final
@RequestMapping(BeerController.API_V1_BEER)
public class BeerController {

    public static final String API_V1_BEER = "/api/v1/beer";
    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 50;
    private final BeerService beerService;

    @GetMapping(produces = { "application/json" })
    @Validated
    @ResponseStatus(HttpStatus.OK)
    public BeerPageList listBeers(@RequestParam(value = "pageNumber") @Positive Integer pageNumber,
                                  @RequestParam(value = "pageSize", required = false) @Positive Integer pageSize,
                                  @RequestParam(value = "beerName", required = false) String beerName,
                                  @RequestParam(value = "beerStyle", required = false) BeerStyleEnum beerStyle) {
        if (pageNumber == null) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        return beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize));
    }

    @GetMapping("/{beerId}")
    @ResponseStatus(HttpStatus.OK)
    public BeerDTO getBeerById(@PathVariable("beerId") UUID beerId) {
        return beerService.getBeerById(beerId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BeerDTO saveNewBeer(@RequestBody @Valid BeerDTO beerDTO) {
        return beerService.saveNewBeer(beerDTO);
    }

    @PutMapping("/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBeerById(@RequestBody @Valid BeerDTO beerDTO, @PathVariable("beerId") UUID beerId) {
        beerService.updateBeer(beerDTO, beerId);
    }

}
