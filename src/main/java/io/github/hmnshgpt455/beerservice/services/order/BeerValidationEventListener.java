package io.github.hmnshgpt455.beerservice.services.order;

import io.github.hmnshgpt455.beerservice.config.JmsConfig;
import io.github.hmnshgpt455.brewery.events.BeerOrderValidationResult;
import io.github.hmnshgpt455.brewery.events.ValidateBeerOrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class BeerValidationEventListener {

    private final BeerOrderValidator beerOrderValidator;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    private void listen(ValidateBeerOrderRequest validateBeerOrderRequest) {
        Boolean isValidOrder = beerOrderValidator.validate(validateBeerOrderRequest.getBeerOrderDto());

        jmsTemplate.convertAndSend(JmsConfig.VALIDATION_RESULT_QUEUE,
                BeerOrderValidationResult.builder()
                    .beerOrderId(validateBeerOrderRequest.getBeerOrderDto().getId())
                    .isValidOrder(isValidOrder)
                    .build());
    }

}
