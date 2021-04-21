package io.github.hmnshgpt455.beerservice.web.controller;

import io.github.hmnshgpt455.beerservice.services.BeerService;
import io.github.hmnshgpt455.brewery.model.BeerDTO;
import io.github.hmnshgpt455.brewery.model.BeerPageList;
import io.github.hmnshgpt455.brewery.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.UUID;

@RestController
@RequiredArgsConstructor //This will inject the dependencies automatically because we have made them final
@RequestMapping(BeerController.API_V1_BEER)
@Validated
public class BeerController {

    public static final String API_V1_BEER = "/api/v1/beer";
    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 50;
    private final BeerService beerService;

    @GetMapping(produces = { "application/json" })
    @ResponseStatus(HttpStatus.OK)
    public BeerPageList listBeers(@RequestParam(value = "pageNumber", required = false) @PositiveOrZero Integer pageNumber,
                                  @RequestParam(value = "pageSize", required = false) @Positive Integer pageSize,
                                  @RequestParam(value = "beerName", required = false) String beerName,
                                  @RequestParam(value = "beerStyle", required = false) BeerStyleEnum beerStyle,
                                  @RequestParam(value = "showInventoryOnHand", defaultValue = "false") Boolean showInventoryOnHand) {
        if (pageNumber == null) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        return beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize), showInventoryOnHand);
    }

    @GetMapping("/{beerId}")
    @ResponseStatus(HttpStatus.OK)
    public BeerDTO getBeerById(@PathVariable("beerId") UUID beerId,
                               @RequestParam(value = "showInventoryOnHand", defaultValue = "false") Boolean showInventoryOnHand) {
        return beerService.getBeerById(beerId, showInventoryOnHand);
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

    @GetMapping("/upc/{upc}")
    public BeerDTO getBeerByUpcCode(@PathVariable("upc") String upc,
                                    @RequestParam(value = "showInventoryOnHand", defaultValue = "false") Boolean showInventoryOnHand) {
        return beerService.getBeerByUpcCode(upc, showInventoryOnHand);
    }

}
