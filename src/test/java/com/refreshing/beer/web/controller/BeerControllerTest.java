package com.refreshing.beer.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.refreshing.beer.bootstrap.BeerLoader;
import com.refreshing.beer.services.BeerService;
import com.refreshing.beer.web.model.BeerDTO;
import com.refreshing.beer.web.model.BeerStyleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    @Test
    void getBeerById() throws Exception {
        given(beerService.getBeerById(any(), anyBoolean())).willReturn(getValidBeerDTO());

        mockMvc.perform(get(BeerController.API_V1_BEER + "/" + UUID.randomUUID().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void saveNewBeer() throws Exception {
        BeerDTO beerDTO = getValidBeerDTO();

        given(beerService.saveNewBeer(any())).willReturn(getValidBeerDTO());

        String beerDtoJson = objectMapper.writeValueAsString(beerDTO);
        mockMvc.perform(post(BeerController.API_V1_BEER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    void updateBeerById() throws Exception {
        BeerDTO beerDTO = getValidBeerDTO();
        String beerDtoJson = objectMapper.writeValueAsString(beerDTO);

        mockMvc.perform(put(BeerController.API_V1_BEER + "/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isNoContent());
    }

    BeerDTO getValidBeerDTO() {
        return BeerDTO.builder()
                .beerName("My beer")
                .beerStyle(BeerStyleEnum.PALE_ALE)
                .price(new BigDecimal("2.99"))
                .upc(BeerLoader.BEER_1_UPC)
                .build();
    }
}