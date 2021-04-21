package io.github.hmnshgpt455.beerservice.services.brewing;

import io.github.hmnshgpt455.beerservice.config.JmsConfig;
import io.github.hmnshgpt455.beerservice.domain.Beer;
import io.github.hmnshgpt455.brewery.events.BrewBeerEvent;
import io.github.hmnshgpt455.beerservice.repositories.BeerRepository;
import io.github.hmnshgpt455.beerservice.services.inventory.BeerInventoryService;
import io.github.hmnshgpt455.beerservice.web.mappers.BeerMapper;
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
