package io.github.hmnshgpt455.beerservice.services.brewing;

import io.github.hmnshgpt455.beerservice.config.JmsConfig;
import io.github.hmnshgpt455.beerservice.domain.Beer;
import io.github.hmnshgpt455.brewery.events.BrewBeerEvent;
import io.github.hmnshgpt455.brewery.events.InventoryEvent;
import io.github.hmnshgpt455.beerservice.repositories.BeerRepository;
import io.github.hmnshgpt455.brewery.model.BeerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrewBeerListener {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    public void listen(BrewBeerEvent beerEvent) {
        BeerDTO beerDTO = beerEvent.getBeerDTO();

        Beer beer = beerRepository.getOne(beerDTO.getId());
        beerDTO.setQuantityOnHand(beer.getQuantityToBrew());

        InventoryEvent inventoryEvent = new InventoryEvent(beerDTO);

        log.debug("Sent inventory event for beer " + beer.getUpc() +
                " with minimum on hand : " + beer.getMinimumOnHand() + " and QOH " + beerDTO.getQuantityOnHand());
        jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, inventoryEvent);
    }
}
