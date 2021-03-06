package io.github.hmnshgpt455.beerservice.services.inventory;

import io.github.hmnshgpt455.brewery.model.BeerInventoryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Profile("!local-discovery")
@Service
@Slf4j
@ConfigurationProperties(prefix = "brewery", ignoreUnknownFields = false)
public class BeerInventoryServiceRestTemplateImpl implements BeerInventoryService {

    private static final String INVENTORY_API_WITH_BEER_ID_PATH = "api/v1/beer/{beerId}/inventory";
    public static final String INVENTORY_API_WITH_BEER_UPC_PATH = "api/v1/beer/upc/{upc}/inventory";
    private String beerInventoryServiceHost;
    private final RestTemplate restTemplate;

    public BeerInventoryServiceRestTemplateImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Integer getOnHandQuantity(UUID beerId) {

        log.debug("Calling inventory service for beer with id : " + beerId);
        ResponseEntity<List<BeerInventoryDTO>> responseEntity = restTemplate.exchange(beerInventoryServiceHost + INVENTORY_API_WITH_BEER_ID_PATH,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<BeerInventoryDTO>>() {}, (Object) beerId);

        return Objects.requireNonNull(responseEntity.getBody())
                                .stream()
                                .mapToInt(BeerInventoryDTO::getQuantityOnHand)
                                .sum();
    }

    @Override
    public Integer getOnHandQuantityByUpc(String upc) {
        log.debug("Calling inventory service for beer with upc : " + upc);
        ResponseEntity<List<BeerInventoryDTO>> responseEntity = restTemplate.exchange(beerInventoryServiceHost + INVENTORY_API_WITH_BEER_UPC_PATH,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<BeerInventoryDTO>>() {}, (Object) upc);

        return Objects.requireNonNull(responseEntity.getBody())
                .stream()
                .mapToInt(BeerInventoryDTO::getQuantityOnHand)
                .sum();
    }

    public void setBeerInventoryServiceHost(String beerInventoryServiceHost) {
        this.beerInventoryServiceHost = beerInventoryServiceHost;
    }
}
