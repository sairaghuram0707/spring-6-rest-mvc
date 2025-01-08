package learn.springframwork.spring6restmvc.services;

import learn.springframwork.spring6restmvc.model.Beer;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface BeerService {
    List<Beer> listBeers();

    Beer getBeerById(UUID id);

    Beer addBeer(Beer beer);
}
