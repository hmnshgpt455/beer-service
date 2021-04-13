package com.refreshing.beer.beerservice.services.brewing;

import com.refreshing.beer.beerservice.config.JmsConfig;
import com.refreshing.beer.beerservice.domain.Beer;
import com.refreshing.beer.beerservice.events.BrewBeerEvent;
import com.refreshing.beer.beerservice.repositories.BeerRepository;
import com.refreshing.beer.beerservice.services.inventory.BeerInventoryService;
import com.refreshing.beer.beerservice.web.mappers.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {

    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Scheduled(fixedRate = 5000) //Will run after every 5 seconds
    public void checkForLowInventoryAndTriggerEvent() {
        List<Beer> beers = beerRepository.findAll();
        beers.forEach(beer -> {
            Integer quantityOnHand = beerInventoryService.getOnHandQuantityByUpc(beer.getUpc());
            log.debug("Beer upc : " + beer.getUpc());
            log.debug("Minimum on hand is : " + beer.getMinimumOnHand());
            log.debug("Quantity on hand is : " + quantityOnHand);
            if (quantityOnHand <= beer.getMinimumOnHand()) {
                jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE, new BrewBeerEvent(beerMapper.beerToBeerDTO(beer)));
            }
        });
    }
}
