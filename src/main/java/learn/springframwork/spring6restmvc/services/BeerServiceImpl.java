package learn.springframwork.spring6restmvc.services;

import learn.springframwork.spring6restmvc.model.Beer;
import learn.springframwork.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
    @Override
    public Beer getBeerById(UUID id) {

        log.debug("Service Implemenation - Get beer with specific ID");

        return Beer.builder()
                .id(id)
                .version(1)
                .beerName("Jamieson")
                .beerStyle(BeerStyle.GOSE)
                .upc("2342")
                .price(new BigDecimal("45.33"))
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
    }
}
