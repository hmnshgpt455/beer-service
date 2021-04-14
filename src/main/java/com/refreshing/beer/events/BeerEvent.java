package com.refreshing.beer.events;

import com.refreshing.beer.web.model.BeerDTO;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BeerEvent implements Serializable {

    static final long serialVersionUID = 4567385095264487200L;

    private BeerDTO beerDTO;
}
