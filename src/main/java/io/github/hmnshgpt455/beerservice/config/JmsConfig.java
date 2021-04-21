package io.github.hmnshgpt455.beerservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {

    public static final String BREWING_REQUEST_QUEUE = "brewing_request";
    public static final String NEW_INVENTORY_QUEUE = "new_inventory";
    public static final String VALIDATE_ORDER_QUEUE = "validate-order";
    public static final String VALIDATION_RESULT_QUEUE = "validate-order-result";

    @Bean
    public MessageConverter jacksonJmsMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter mappingJackson2MessageConverter = new MappingJackson2MessageConverter();
        mappingJackson2MessageConverter.setTargetType(MessageType.TEXT);
        mappingJackson2MessageConverter.setTypeIdPropertyName("_type");
        mappingJackson2MessageConverter.setObjectMapper(objectMapper);
        return mappingJackson2MessageConverter;
    }
}
