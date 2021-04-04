package com.refreshing.beer.beerservice.web.controller;

import com.refreshing.beer.beerservice.web.model.BeerDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(BeerController.API_V1_BEER)
public class BeerController {

    public static final String API_V1_BEER = "/api/v1/beer";

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDTO> getBeerById(@PathVariable("beerId") UUID beerId) {
        return new ResponseEntity<>(BeerDTO.builder().build(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BeerDTO> saveNewBeer(@RequestBody @Validated BeerDTO beerDTO) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity<BeerDTO> updateBeerById(@RequestBody @Validated BeerDTO beerDTO, @PathVariable("beerId") UUID beerId) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
