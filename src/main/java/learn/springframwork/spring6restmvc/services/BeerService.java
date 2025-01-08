package learn.springframwork.spring6restmvc.services;

import learn.springframwork.spring6restmvc.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {
    List<Beer> listBeers();

    Beer getBeerById(UUID id);

    Beer addBeer(Beer beer);

    void updateBeerById(UUID beerId,Beer beer);

    void deleteBeerById(UUID beerId);

    void patchBeerById(UUID beerId, Beer beer);
}
