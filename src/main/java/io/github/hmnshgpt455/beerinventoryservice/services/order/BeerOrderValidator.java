package io.github.hmnshgpt455.beerinventoryservice.services.order;

import io.github.hmnshgpt455.beerinventoryservice.domain.Beer;
import io.github.hmnshgpt455.beerinventoryservice.repositories.BeerRepository;
import io.github.hmnshgpt455.brewery.model.BeerOrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class BeerOrderValidator {

    private final BeerRepository beerRepository;

    public boolean validate(BeerOrderDto beerOrder) {
        Set<String> validUpcList = beerRepository.findAll().stream()
                .map(Beer::getUpc).collect(Collectors.toSet());

        if (beerOrder != null && beerOrder.getBeerOrderLines() != null && !beerOrder.getBeerOrderLines().isEmpty()) {

            return beerOrder.getBeerOrderLines()
                    .stream().reduce(true, (res, orderLine) -> res && validUpcList.contains(orderLine.getUpc()),
                            BeerOrderValidator::combiner);
        }

        return false;


    }

    private static Boolean combiner(Boolean accumulator, Boolean result) {
        return accumulator && result;
    }
}
